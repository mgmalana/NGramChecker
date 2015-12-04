package models;

import java.util.ArrayList;
import java.util.List;

public class ErrorWeight {

	private int nSubstituteDiffLemma;
	private int nSubstituteSameLemma;
	private int nInsert;
	private int nDelete;
	private int nMerge;
	private int nUnmerge;

	private List<Action> actionsForSubstituteDiffLemma;
	private List<Action> actionsForSubstituteSameLemma;
	private List<Action> actionsForInsert;
	private List<Action> actionsForDelete;
	private List<Action> actionsForMerge;
	private List<Action> actionsForUnmerge;

	public ErrorWeight() {
		super();
		nSubstituteDiffLemma = 0;
		nSubstituteSameLemma = 0;
		nInsert = 0;
		nDelete = 0;
		nMerge = 0;
		nUnmerge = 0;
		actionsForSubstituteDiffLemma = new ArrayList<>();
		actionsForSubstituteSameLemma = new ArrayList<>();
		actionsForInsert = new ArrayList<>();
		actionsForDelete = new ArrayList<>();
		actionsForMerge = new ArrayList<>();
		actionsForUnmerge = new ArrayList<>();
	}

	public void addNSubstituteDiffLemma(Action action) {
		nSubstituteDiffLemma += 1;
		actionsForSubstituteDiffLemma.add(action);
	}

	public void addNSubstituteSameLemma(Action action) {
		nSubstituteSameLemma += 1;
		actionsForSubstituteSameLemma.add(action);
	}

	public void addNInsert(Action action) {
		nInsert += 1;
		actionsForInsert.add(action);
	}

	public void addNDelete(Action action) {
		nDelete += 1;
		actionsForDelete.add(action);
	}

	public void addNMerge(Action action) {
		nDelete += 1;
		actionsForMerge.add(action);
	}

	public void addNUnmerge(Action action) {
		nDelete += 1;
		actionsForUnmerge.add(action);
	}

	public List<Action> getActionsForUnmerge() {
		return actionsForUnmerge;
	}

	public List<Action> getActionsForSubstituteDiffLemma() {
		return actionsForSubstituteDiffLemma;
	}

	public List<Action> getActionsForSubstituteSameLemma() {
		return actionsForSubstituteSameLemma;
	}

	public List<Action> getActionsForInsert() {
		return actionsForInsert;
	}

	public List<Action> getActionsForDelete() {
		return actionsForDelete;
	}

	public List<Action> getActionsForMerge() {
		return actionsForMerge;
	}

}
