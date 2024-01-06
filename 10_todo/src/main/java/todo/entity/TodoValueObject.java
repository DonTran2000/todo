package todo.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TodoValueObject {

	Integer id;
	String title;
	String task;
	Timestamp limitdate;
	Timestamp lastupdate;
	String userid;
	Integer status;
	String label;
	String inputLimitDate;
	private String filename;
	
	private List<String> errorMessages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Timestamp getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(Timestamp limitdate) {
		this.limitdate = limitdate;
	}

	public Timestamp getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInputLimit() {
		return inputLimitDate;
	}
	
	public void setInputLimitdate(String inputLimitdate) {
		this.inputLimitDate = inputLimitdate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

	public boolean valueCheck() {
		errorMessages = new ArrayList<String>();
		
		//id
		if (id < 0) {
			errorMessages.add("不正な入力を検出しました");
		}
		
		//title
		if (title == null || title.isEmpty()) {
			errorMessages.add("入力したタイトルが空です");
		} else if (title.length() > 256) {
			errorMessages.add("入力したタイトルが長いすぎます");
		}
		
		//task
		if (task == null || task.isEmpty()) {
			errorMessages.add("入力したタスクが空です");
		} else if (title.length() > 512) {
			errorMessages.add("入力したタスクが長いすぎます");
		}
		
		//limitdate
		if (inputLimitDate == null || inputLimitDate.isEmpty()) {
			errorMessages.add("入力したタスク期限が空です");
		} else if (inputLimitDate.matches("\\d{4}-\\d{2}-\\d{2}") ){
			errorMessages.add("入力したタスク期限のフォーマットが違います");
		}
		
		//userid
		if (userid == null || userid.isEmpty()) {
			errorMessages.add("入力したユーザーIDが空です");
		} else if (userid.length() > 64) {
			errorMessages.add("入力したユーザーIDが長いすぎます");
		}
		
		//status
		if (status < 0 || status > 3) {
			errorMessages.add("入力したStatusの値が不正です");
		}
		
		return (errorMessages.size() == 0);
	}
	
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
