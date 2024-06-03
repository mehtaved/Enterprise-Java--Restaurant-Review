package ca.sheridancollege.mehtaved.beans;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Poll {

	private Long id;
	private String title;
	private String Question;
	private String answer1;
	private String answer2;
	private String answer3;

	private int votes1;
	private int votes2;
	private int votes3;

	private String date;

	private double percentage1;
	private double percentage2;
	private double percentage3;

	public Poll(Long id, String title, String Question, String answer1, String answer2, String answer3, String date) {
		this.id = id;
		this.title = title;
		this.Question = Question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.date = date;
	}


	public void calculatePercentages() {
		int totalVotes = votes1 + votes2 + votes3;
		if (totalVotes > 0) {
			percentage1 = (votes1 * 100.0) / totalVotes;
			percentage2 = (votes2 * 100.0) / totalVotes;
			percentage3 = (votes3 * 100.0) / totalVotes;
		}
	}

	

	@Override
	public boolean equals(Object m) {
		if (this == m)
			return true;
		if (m == null || getClass() != m.getClass())
			return false;
		Poll poll = (Poll) m;
		return Objects.equals(id, poll.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
