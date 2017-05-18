package hr.eestec_zg.frmsbackend.domain.models.dto;

import hr.eestec_zg.frmsbackend.domain.models.SponsorshipType;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;

import java.time.ZonedDateTime;

public class TaskDto {
    private Long id;
    private Long eventId;
    private Long companyId;
    private Long userId;
    private SponsorshipType type;
    private ZonedDateTime callTime;
    private ZonedDateTime mailTime;
    private ZonedDateTime followUpTime;
    private TaskStatus status;
    private String notes;

    public TaskDto() {}

    public TaskDto(Long eventId, Long companyId, Long userId, SponsorshipType type, ZonedDateTime callTime,
                ZonedDateTime mailTime, ZonedDateTime followUpTime, TaskStatus status, String notes) {
        this.eventId = eventId;
        this.companyId = companyId;
        this.userId = userId;
        this.type = type;
        this.callTime = callTime;
        this.mailTime = mailTime;
        this.followUpTime = followUpTime;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getEventId() { return eventId; }

    public void setEventId (long eventId) {
        this.eventId = eventId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
