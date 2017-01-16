package hr.eestec_zg.frmsbackend.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Company company;
    @ManyToOne
    private User assignee;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SponsorshipType type;
    @Column
    private ZonedDateTime callTime;
    @Column
    private ZonedDateTime mailTime;
    @Column
    private ZonedDateTime followUpTime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column
    private String notes;

    public Task() {}

    public Task(Event event, Company company, User assignee, SponsorshipType type, ZonedDateTime callTime,
                ZonedDateTime mailTime, ZonedDateTime followUpTime, TaskStatus status, String notes) {
        this.event = event;
        this.company = company;
        this.assignee = assignee;
        this.type = type;
        this.callTime = callTime;
        this.mailTime = mailTime;
        this.followUpTime = followUpTime;
        this.status = status;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public SponsorshipType getType() {
        return type;
    }

    public void setType(SponsorshipType type) {
        this.type = type;
    }

    public ZonedDateTime getCallTime() {
        return callTime;
    }

    public void setCallTime(ZonedDateTime callTime) {
        this.callTime = callTime;
    }

    public ZonedDateTime getMailTime() {
        return mailTime;
    }

    public void setMailTime(ZonedDateTime mailTime) {
        this.mailTime = mailTime;
    }

    public ZonedDateTime getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(ZonedDateTime followUpTime) {
        this.followUpTime = followUpTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id == task.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", event=" + event +
                ", company=" + company +
                ", assignee=" + assignee +
                ", type=" + type +
                ", callTime=" + callTime +
                ", mailTime=" + mailTime +
                ", followUpTime=" + followUpTime +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }
}
