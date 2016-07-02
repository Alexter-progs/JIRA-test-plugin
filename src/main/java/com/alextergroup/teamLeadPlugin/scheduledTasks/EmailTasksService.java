package com.alextergroup.teamLeadPlugin.scheduledTasks;

import com.atlassian.configurable.ObjectConfiguration;
import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.mail.Email;
import com.atlassian.jira.service.AbstractService;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.mail.queue.MailQueue;
import com.atlassian.mail.queue.SingleMailQueueItem;
import com.opensymphony.module.propertyset.PropertySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import au.com.bytecode.opencsv.CSVWriter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EmailTasksService extends AbstractService {

    private final  Logger log = LoggerFactory.getLogger(EmailTasksService.class);
    private final  SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class);
    private final ApplicationUser logeInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    private final MailQueue mailQueue = ComponentAccessor.getMailQueue();

    private String jqlQuery = "status = \"in Progress\"";

    private String emailTo;
    private String mailMessage = "This is mailing issues to csv service. You can find CSV file with issues in attachments!";
    private String mailMimeType = "text/plain";
    private String mailSubject = "CSV Issue export";
    private String CSVFileName = "exportedIssues.csv";

    @Override
    public void run() {
        log.error("CSV email sender service started...");

        Email email = new Email(emailTo);
        email.setSubject(mailSubject);
        email.setMimeType(mailMimeType);
        email.setBody(mailMessage);

        try {
            Multipart mailMultiPart = new MimeMultipart();
            BodyPart attachBody = new MimeBodyPart();

            final File attachmentFile = createCSVWithIssues();
            if(attachmentFile == null){
                log.error("An unexpected error occurred. CSV file cannot be created. Please contact alexplotbox@gmail.com");
                return;
            }

            attachBody.setFileName(CSVFileName);
            FileDataSource dataSource = new FileDataSource(attachmentFile);
            attachBody.setDataHandler(new DataHandler(dataSource));
            mailMultiPart.addBodyPart(attachBody);
            email.setMultipart(mailMultiPart);

        } catch (MessagingException e){
            log.error("An error occurred while ", e);
        }

        SingleMailQueueItem item = new SingleMailQueueItem(email);
        mailQueue.addItem(item);
    }

    private File createCSVWithIssues(){
        try {
            File csvFile = File.createTempFile("temp", "csv");
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
            CSVWriter csvWriter = new CSVWriter(bw, ',','\"');
            List<String[]> paramList = new ArrayList<>();

            List<Issue> issues = getIssues(jqlQuery);
            for(Issue issue : issues){
                String[] issueParams = {
                        issue.getSummary(),
                        issue.getAssigneeId(),
                        issue.getCreated().toString(),
                        issue.getCreator().getUsername(),
                        issue.getCreatorId(),
                        issue.getDescription(),
                        issue.getEnvironment(),
                        String.valueOf(issue.getEstimate()),
                        String.valueOf(issue.getId()),
                        issue.getIssueTypeId(),
                        issue.getKey(),
                        String.valueOf(issue.getNumber()),
                        String.valueOf(issue.getOriginalEstimate()),
                        String.valueOf(issue.getParentId()),
                        String.valueOf(issue.getProjectId()),
                        issue.getReporterId(),
                        issue.getReporterUser().getName()
                };
                paramList.add(issueParams);
            }
            String[] summary = new String[] {
                "Summary", "AssigneeId", "Created", "Creator", "CreatorId", "Description",
                "Environment", "Estimate", "Id", "IssueTypeId", "Key", "Number",  "OriginalEstimate",
                "ParentId", "ProjectId", "ReporterId", "Reporter"};

            csvWriter.writeNext(summary);
            csvWriter.writeAll(paramList);

            csvWriter.close();
            bw.close();

            return csvFile;
        } catch (IOException e){
            log.error("An error occurred while creating CSV file: ", e);
        }
        return null;
    }

    private List<Issue> getIssues(String query){
        final SearchService.ParseResult parseResult = searchService.parseQuery(logeInUser, query);
        if (parseResult.isValid()) {
            try {
                final SearchResults results = searchService.search(logeInUser, parseResult.getQuery(), PagerFilter.getUnlimitedFilter());
                return results.getIssues();
            } catch (SearchException e) {
                log.error("Error running search", e);
            }
        } else {
            log.error("Error parsing jqlQuery: " + parseResult.getErrors());
        }
        return Collections.emptyList();
    }

    @Override
    public ObjectConfiguration getObjectConfiguration() throws ObjectConfigurationException {
        return getObjectConfiguration("csv8", "services/serviceconfig.xml", null);
    }

    @Override
    public void init(PropertySet props) throws ObjectConfigurationException {
        super.init(props);
        if(hasProperty("EMAIL")){
            emailTo = getProperty("EMAIL");
        }
    }
}
