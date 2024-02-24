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

public class CardRepository {
	
	private final String SAVE_SQL = "INSERT INTO card(id, name, damage, elementType, cardType, userId, deck) VALUES(?,?,?,?,?,?,?)";
	private final String SELECT_FIRST_FIVE_BY_USERID_SQL = "SELECT name, id, userID FROM card WHERE userID LIKE 'none-%' ORDER BY userID LIMIT 5";
	private final String ACQUIRE_SQL = "UPDATE card SET userID = ? WHERE id = ?";
	private final String FIND_ALL_SQL = "SELECT * FROM card";
	private final String SELECT_DECK_FROM_USERID_SQL = "SELECT * FROM card WHERE deck = true";
	private final String SET_DECK_SQL = "UPDATE card SET deck = ? WHERE id = ?";
	private final String UNDECK_FOR_TRADE_SQL = "UPDATE `card` SET userID = 0 AND deck = 0 WHERE id = ?";
	private final String UPDATE_USERID_SQL = "UPDATE `card` SET userID = ? WHERE id = ?";
	
	
	private final Database database = new Database();
	
	public Card save(Card card) {
		try (
			Connection con = database.getConnection();
			PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
		) {
			String elementType = "";
			String cardType = "";
			if(card.getElementType() != null)elementType = card.getElementType().toString();
			if(card.getCardType() != null)cardType = card.getCardType().toString();
			pstmt.setString(1, card.getId());
			pstmt.setString(2, card.getName());
			pstmt.setDouble(3, card.getDamage());
			pstmt.setString(4, elementType);
			pstmt.setString(5, cardType);
			pstmt.setString(6, card.getUserId());
			pstmt.setBoolean(7, card.getDeck());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return card;
	}
	
	public List<String> getFirstFiveCardId(String userId) {
		
		List<String> cardIds = new ArrayList<>();
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT_FIRST_FIVE_BY_USERID_SQL);
				ResultSet rs = pstmt.executeQuery()
		) {
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				cardIds.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(cardIds.size()<5)return null;
		return cardIds;
	}
	
	public boolean acquire(String cardId, String userId) {
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(ACQUIRE_SQL)
		) {
			pstmt.setString(1, userId);
			pstmt.setString(2, cardId);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Card> showAllByUserid(String userId){
		List<Card> cards = new ArrayList<>();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("userID").equals(userId)) {
					Card card = new Card(
							rs.getString("id"),
							rs.getString("name"),
							rs.getInt("damage"),
							ElementType.valueOf(rs.getString("elementType")),
							CardType.valueOf(rs.getString("cardType")),
							rs.getString("userId"),
							rs.getBoolean("deck")
					);
					cards.add(card);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		return cards;
	}
	
	public List<Card> showDeck(String userId){
		List<Card> cards = new ArrayList<>();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT_DECK_FROM_USERID_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("userID").equals(userId)) {
					Card card = new Card(
							rs.getString("id"),
							rs.getString("name"),
							rs.getInt("damage"),
							ElementType.valueOf(rs.getString("elementType")),
							CardType.valueOf(rs.getString("cardType")),
							rs.getString("userId"),
							rs.getBoolean("deck")
					);
					cards.add(card);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cards;
	}
	
	public boolean cardsBelongToUser(List<String> deckByIds, String userId) {
		
		int i = 0;
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("userID").equals(userId)) {
					for(String cardId : deckByIds) {
						if(rs.getString("id").equals(cardId)) {
							i++;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		if(i>=4)return true;
		return false;
	}
	
	public void configureDeck(List<String> deckByIds, String userId) {

		//find all cards from user
		List<String> stackFromUserById = new ArrayList<>();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("userID").equals(userId)) {
					stackFromUserById.add(rs.getString("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		
		//set all cards attribute of deck to false
		//and set the ones chosen in deckByIds to true
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SET_DECK_SQL)
		) {
			for(String cardId : stackFromUserById) {
				pstmt.setBoolean(1, false);
				for(String cardIdByDeck : deckByIds) {
					if(cardIdByDeck.equals(cardId))
					{
						pstmt.setBoolean(1, true);
					}
				}
				pstmt.setString(2, cardId);
				
				pstmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public List<Card> getDeckForBattle(String userId){
		
		
		List<Card> deck = new ArrayList<>();
		try(
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
				ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				if(rs.getString("userID").equals(userId)) {
					if(rs.getBoolean("deck")) {
						Card card = new Card(
								rs.getString("id"),
								rs.getString("name"),
								rs.getInt("damage"),
								ElementType.valueOf(rs.getString("elementType")),
								CardType.valueOf(rs.getString("cardType")),
								rs.getString("userId"),
								rs.getBoolean("deck")
						);
						deck.add(card);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return deck;
	}

	public boolean undeckCard(String cardtotrade) {
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UNDECK_FOR_TRADE_SQL)
		) {
			pstmt.setString(1, cardtotrade);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deckCard(String userId, String cardid) {
		
		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_USERID_SQL)
		) {
			pstmt.setString(1, userId);
			pstmt.setString(2, cardid);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean tradeCards(String userId1, String cardId1, String userId2, String cardId2) {

		try (
				Connection con = database.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_USERID_SQL)
		) {
			pstmt.setString(1, userId1);
			pstmt.setString(2, cardId2);
			
			pstmt.execute();
			
			pstmt.setString(1, userId2);
			pstmt.setString(2, cardId1);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
