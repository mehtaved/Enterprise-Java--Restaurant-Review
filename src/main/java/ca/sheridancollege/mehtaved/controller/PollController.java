/*Name: Vedant Mehta
 * Student Id: 991679825
 * Date: 18-10-2023*/
package ca.sheridancollege.mehtaved.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.mehtaved.beans.Poll;
import ca.sheridancollege.mehtaved.database.DatabaseAccess;

@Controller
public class PollController {

	private final DatabaseAccess databaseAccess;

	public PollController(DatabaseAccess databaseAccess) {
		this.databaseAccess = databaseAccess;
	}

	@GetMapping("/")
	public String showPolls(Model model) {
		List<Poll> polls = databaseAccess.getAllPolls();
		model.addAttribute("polls", polls);
		return "index2";
	}

	@GetMapping("/poll")
	public String showPollForm(@RequestParam Long pollId, Model model) {
		Poll poll = databaseAccess.getPollWithResults(pollId);
		model.addAttribute("poll", poll);
		return "index1";
	}

	@PostMapping("/poll")
	public String submitPoll(@RequestParam Long pollId, @RequestParam int selectedOption) {
		databaseAccess.insertPollResponse(pollId, selectedOption);
		return "redirect:/result?pollId=" + pollId;
	}

	@GetMapping("/result")
	public String showResults(@RequestParam Long pollId, Model model) {
		Poll poll = databaseAccess.getPollWithResults(pollId);
		model.addAttribute("poll", poll);
		return "result";
	}
}
