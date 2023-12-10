public class Task {

  private String name;
  private String rank;
  private String subject;
  private String note;

  /**
   * Constructor used to make a generic task
   */
  public Task()
  {
    this.name = "name";
    this.rank = "rank";
    this.subject = "subject";
    this.note = "note";
  }
  
  public Task(String name, String rank, String subject, String note)
  {
    this.name = name;
    this.rank = rank;
    this.subject = subject;
    this.note = note;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getrank() {
    return rank;
  }
  /**
   * Method used to set the task's rank
   */
  public void setrank(String rank) {
    this.rank = rank;
  }
  /**
   * Method used to set the task's subject
   * @param subject the task's subject
   */
  public void setsubject(String subject) {
    this.subject = subject;
  }
  /**
   * Method used to return the subject of the task
   * @return the task's subject
   */
  public String getsubject() {
    return subject;
  }

  /**
   * Method used to set the task notes
   * @param subject the task notes
   */
  public void setNote(String note) {
    this.note = note;
  }
  /**
   * Method used to return the task notes
   * @return the task notes
   */
  public String getNote() {
    return note;
  }
  /**
   * Method used to return a textual representation of the task
   * @return the representation of the task
   */
  public String toString() {
    return "Name: "+ getName() + "\n" + "\n" + "rank: "+ getrank() + "\n" + "subject: " + getsubject() + "Note: " + getNote();
  }
  public String toStringExport() {
    return getName() +"�"+ getrank() + "�"+ getsubject() +"�"+ getNote();
  }
}
