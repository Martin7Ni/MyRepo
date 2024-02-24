package at.technikum.apps.mtcg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.CardType;
import at.technikum.apps.mtcg.entity.ElementType;
import at.technikum.apps.mtcg.entity.Trade;

public class TradingRepository {
	
	private final String FIND_ALL_SQL = "SELECT * FROM trade";
	private final String SAVE_SQL = "INSERT INTO `trade`(`id`, `userid`, `cardtotrade`, `type`, `minimumdamage`, `traded`) VALUES (?,?,?,?,?,?)";
	private final String DELETE_SQL = "DELETE FROM `trade` WHERE `id` = ?";
	private final String SET_TRADED_SQL = "UPDATE `trade` SET `traded`= 1 WHERE `id` = ?";
	
	private final Database database = new Database();

	public List<Trade> check(String userId) {
		List<Trade> trades = new ArrayList<>();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getBoolean("traded"))continue;
				if(rs.getString("userID").equals(userId)) {
					Trade trade = new Trade(
							rs.getString("id"),
							rs.getString("userId")+"(YOU)",
							rs.getString("cardtotrade"),
							rs.getString("type"),
							rs.getDouble("minimumdamage"),
							rs.getBoolean("traded")
					);
					trades.add(trade);
				} else {
					Trade trade = new Trade(
							rs.getString("id"),
							rs.getString("userId"),
							rs.getString("cardtotrade"),
							rs.getString("type"),
							rs.getDouble("minimumdamage"),
							rs.getBoolean("traded")
					);
					trades.add(trade);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		return trades;
	}

	public Trade createOffer(Trade trade) {
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
			) {
				pstmt.setString(1, trade.getId());
				pstmt.setString(2, trade.getUserid());
				pstmt.setString(3, trade.getCardtotrade());
				pstmt.setString(4, trade.getType());
				pstmt.setDouble(5, trade.getMinimumdamage());
				pstmt.setBoolean(6, trade.isTraded());
				
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
			return trade;
	}

	public Trade delete(String id, String userId) {
		Trade trade = null;
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getBoolean("traded"))continue;
				if(!rs.getString("id").equals(id))continue;
				trade = new Trade(
						rs.getString("id"),
						rs.getString("userId"),
						rs.getString("cardtotrade"),
						rs.getString("type"),
						rs.getDouble("minimumdamage"),
						rs.getBoolean("traded")
				);
				break;
			}
		} catch (SQLException e) {
			return null;
		}

		try (
			Connection con = database.getConnection();
			PreparedStatement pstmt = con.prepareStatement(DELETE_SQL)
		) {
			pstmt.setString(1, id);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return trade;
	}

	public Trade getTrade(String id, String userId) {
		Trade trade = null;
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getBoolean("traded"))continue;
				if(rs.getString("id").equals(id)) {
					trade = new Trade(
							rs.getString("id"),
							rs.getString("userId"),
							rs.getString("cardtotrade"),
							rs.getString("type"),
							rs.getDouble("minimumdamage"),
							rs.getBoolean("traded")
					);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		if(userId.equals(trade.getUserid()))return null;

		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SET_TRADED_SQL)
			) {
				pstmt.setString(1, id);
				
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		
		return trade;
	}

}
