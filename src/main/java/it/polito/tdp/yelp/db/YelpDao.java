package it.polito.tdp.yelp.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;
import it.polito.tdp.yelp.model.Adiacenza;

public class YelpDao {

	public List<Business> getAllBusiness(){
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void getAllUsersMappa(Map<String,User> idMap){
		
		String sql = "SELECT * FROM Users";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				if(! idMap.containsKey(res.getString("user_id"))) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				idMap.put(res.getString("user_id"), user);
				}
			}
			res.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	public List<User> creaVertici(int Nrecensioni, Map<String,User> idMap) {
		
		String sql = "SELECT DISTINCT * "
				+ "FROM users u, reviews r "
				+ "WHERE u.user_id=r.user_id "
				+ "GROUP BY u.user_id "
				+ "HAVING COUNT(*) >=? ";
		
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Nrecensioni);
			ResultSet res = st.executeQuery();
			while (res.next()) {
						
				User user = new User(res.getString("user_id"),
							res.getInt("votes_funny"),
							res.getInt("votes_useful"),
							res.getInt("votes_cool"),
							res.getString("name"),
							res.getDouble("average_stars"),
							res.getInt("review_count"));
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> creaArchi(int anno, int Nrecensioni, Map<String,User> idMap) {
		
		String sql = "SELECT DISTINCT u1.user_id AS ut1, u2.user_id AS ut2, COUNT(*) AS peso "
				    + "FROM users u1, users u2, reviews r1, reviews r2 "
				    + "WHERE u1.user_id=r1.user_id AND u2.user_id=r2.user_id "
				    + "AND r1.business_id=r2.business_id  "
			     	+ "AND u1.user_id>u2.user_id "
			    	+ "AND YEAR(r1.review_date)= ? AND YEAR(r1.review_date)=YEAR(r2.review_date) "
				    + "AND u1.user_id IN(SELECT DISTINCT u.user_id "
				    + "                  FROM users u, reviews r "
				    + "                  WHERE u.user_id=r.user_id "
			   	    + "                  GROUP BY u.user_id "
				    + "                  HAVING COUNT(*)>= ? ) "
				    + "                   "
			     	+ "AND u2.user_id IN(SELECT DISTINCT u.user_id "
			     	+ "                  FROM users u, reviews r "
			    	+ "                  WHERE u.user_id=r.user_id "
			    	+ "                  GROUP BY u.user_id "
			    	+ "                  HAVING COUNT(*)>= ? ) "
			    	+ "                   "
			    	+ "GROUP BY u1.user_id, u2.user_id ";
		
		List<Adiacenza> result = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, Nrecensioni);
			st.setInt(3, Nrecensioni);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
						
				Adiacenza a = new Adiacenza(idMap.get(res.getString("ut1")),
						                    idMap.get(res.getString("ut2")),
							                 res.getInt("peso"));
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
}
