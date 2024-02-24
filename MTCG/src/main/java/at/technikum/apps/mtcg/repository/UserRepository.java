package at.technikum.apps.mtcg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;

public class UserRepository {
	
	private final String FIND_ALL_SQL = "SELECT * FROM user";
	private final String SAVE_SQL = "INSERT INTO user(id, username, password, coins, eloValue, win, loss, bio, image, token, name) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private final String LOGIN_SQL = "SELECT token, username, password FROM user";
	private final String FIND_BY_TOKEN_SQL = "SELECT token, id, coins FROM user";
	private final String SPENT_COINS_SQL = "UPDATE user SET coins = coins -5 WHERE token = ?";
	private final String FIND_ID_BY_TOKEN_SQL = "SELECT id, token FROM user";
	private final String UPDATE_USER_SQL = "UPDATE user SET name = ?, bio = ?, image = ? WHERE token = ?";
	private final String USER_WON_SQL = "UPDATE `user` SET `eloValue`=`eloValue`+3,`win`=`win`+1 WHERE `id` = ?";
	private final String USER_LOSE_SQL = "UPDATE `user` SET `eloValue`=`eloValue`-5,`loss`=`loss`+1 WHERE `id` = ?";
	
	private final Database database = new Database();
	
	public User save(User user) {
		System.out.println(user.getUsername()+"-"+user.getPassword());
		
		/*try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			while(rs.next()) {
				if(rs.getString("username")==user.getUsername()) {
					throw new IllegalStateException("Username already taken.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}*/
		
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
		) {
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.getCoin());
			pstmt.setInt(5, user.getEloValue());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setString(8, "");
			pstmt.setString(9, "");
			pstmt.setString(10, "Bearer "+user.getUsername()+"-mtcgToken");
			pstmt.setString(11, "");
			
			pstmt.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public String login(User user) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(LOGIN_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {

			while (rs.next()) {
				if(user.getUsername().equals(rs.getString("username")) && 
					user.getPassword().equals(rs.getString("password"))) {
					return rs.getString("token");
				}
				System.out.println("----");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUserIdForPackageTransaction(String token) {
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_BY_TOKEN_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			
			while (rs.next()) {
				if(rs.getString("token").equals(token) && rs.getInt("coins") >= 5) {
					return rs.getString("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean spendCoins(String token) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SPENT_COINS_SQL);
		) {
			pstmt.setString(1, token);
			
			pstmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getIdFromToken(String token) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ID_BY_TOKEN_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("token").equals(token)) {
					return rs.getString("ID");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public User get(String username, String token) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			while(rs.next()) {
				if(rs.getString("username").equals(username) && rs.getString("token").equals(token)) {
					return new User(rs.getString("id"), 
							rs.getString("username"), 
							rs.getString("password"), 
							rs.getInt("coins"), 
							rs.getInt("eloValue"), 
							rs.getInt("win"), 
							rs.getInt("loss"), 
							rs.getString("name"),
							rs.getString("bio"), 
							rs.getString("image"), 
							rs.getString("token"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	public User update(User user) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_SQL);
		) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getBio());
			pstmt.setString(3, user.getImage());
			pstmt.setString(4, user.getToken());
			
			pstmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}

	public String getStats(String token) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			while(rs.next()) {
				if(rs.getString("token").equals(token)) {
					return rs.getInt("eloValue")+"|"+rs.getInt("win")+"|"+rs.getInt("loss");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}

	public String scoreboard(String token) {
		
		String scoreboard = new String();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			while(rs.next()) {
				if(rs.getString("token").equals(token)) {
					scoreboard = scoreboard+rs.getInt("eloValue")+"\t"+rs.getInt("win")+"\t"+rs.getInt("loss")+"\t"+rs.getString("username")+"(YOU)\t\n";
					continue;
				}
				scoreboard = scoreboard+rs.getInt("eloValue")+"\t"+rs.getInt("win")+"\t"+rs.getInt("loss")+"\t"+rs.getString("username")+"\t\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
		
		return scoreboard;
	}

	public void won(String userId) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(USER_WON_SQL);
		) {
			pstmt.setString(1, userId);
			
			pstmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("winner update");
	}

	public void lose(String userId) {
		
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(USER_LOSE_SQL);
		) {
			pstmt.setString(1, userId);
			
			pstmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("loser update");
	}
}
