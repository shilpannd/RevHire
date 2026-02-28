package com.RevHire.RevHire1;

import com.RevHire.RevHire1.models.*;
import com.RevHire.RevHire1.service.*;
import java.util.Scanner;
import java.util.List;

public class view {

    private JobsService jobService = new JobsService();
    private JobApplicationsService applicationService = new JobApplicationsService();
    private JobSeekerProfileService profileService = new JobSeekerProfileService();
    private ExperienceService experienceService = new ExperienceService();
    private EducationService educationService = new EducationService();
    private ResumeService resumeService = new ResumeService();
    private EmployerProfileService employerService = new EmployerProfileService();
    private SkillsService skillsService = new SkillsService();
    private ResumeSkillsService resumeSkillsService = new ResumeSkillsService();
    private ProjectsService projectService = new ProjectsService();
    private CertificationsService certificationService = new CertificationsService();
    private JobSkillsService jobSkillsService = new JobSkillsService();
    private NotificationsService notificationService = new NotificationsService();

    private Scanner scanner = new Scanner(System.in);

    public void displayLoginMenuWithUser(Users loggedInUser) {
        if (loggedInUser.getRole().equalsIgnoreCase("JOB_SEEKER")) {
            int userId = loggedInUser.getUserId();
            int resumeId = resumeService.getResumeIdByUserId(userId); 
            
            if (resumeId <= 0) {
                System.out.println("Setting up your profile for the first time...");
                resumeId = resumeService.createResume(userId); 
            }
            displayJobSeekerDashboard(userId);
        } else {
            displayEmployerDashboard(loggedInUser.getUserId());
        }
    }

    private void displayJobSeekerDashboard(int userId) {
        boolean logout = false;
        while (!logout) {
            List<Notifications> allNotifs = notificationService.getNotificationsForUser(userId);
            long unreadCount = allNotifs.stream().filter(n -> !n.isRead()).count();
            
            System.out.println("\n===== JOB SEEKER DASHBOARD =====");
            if (unreadCount > 0) {
                System.out.println("🔔 YOU HAVE " + unreadCount + " NEW NOTIFICATIONS!");
            }

            System.out.println("1. Profile & Resume Management");
            System.out.println("2. Search & Apply for Jobs");
            System.out.println("3. View My Applications");
            System.out.println("4. View Notifications (" + unreadCount + " new)");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine()); 

                switch (choice) {
                    case 1: handleResumeManagement(userId); break;
                    case 2: handleJobSearch(userId); break;
                    case 3: handleViewApplications(userId); break;
                    case 4: handleViewNotifications(userId); break; 
                    case 5: logout = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private void handleJobSearch(int userId) {
        System.out.println("\n--- AVAILABLE JOBS ---");
        List<Jobs> jobs = jobService.getAllOpenJobs(); 

        if (jobs.isEmpty()) {
            System.out.println("No jobs are currently posted.");
            return;
        }

        System.out.printf("%-5s | %-25s | %-15s | %-10s%n", "ID", "Job Title", "Location", "Status");
        System.out.println("-------------------------------------------------------------------------");
        for (Jobs job : jobs) {
            System.out.printf("%-5d | %-25s | %-15s | %-10s%n", 
                job.getJobId(), job.getTitle(), job.getLocation(), job.getStatus());
        }

        System.out.print("\nEnter Job ID to apply (or 0 to search by specific keyword): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.print("Enter job title or keyword: ");
                String keyword = scanner.nextLine();
                List<Jobs> filteredJobs = jobService.searchJobs(keyword);
                
                if (filteredJobs.isEmpty()) {
                    System.out.println("No jobs found for that keyword.");
                } else {
                    System.out.println("\n--- SEARCH RESULTS ---");
                    filteredJobs.forEach(j -> System.out.println("ID: " + j.getJobId() + " | Title: " + j.getTitle()));
                    System.out.print("Enter Job ID to apply: ");
                    int newId = Integer.parseInt(scanner.nextLine());
                    handlePreApplyMenu(userId, newId);
                }
            } else {
                handlePreApplyMenu(userId, choice);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Returning to dashboard.");
        }
    }

    private void handlePreApplyMenu(int userId, int jobId) {
        boolean finished = false;
        while (!finished) {
            System.out.println("\n--- PRE-APPLICATION CHECK ---");
            System.out.println("1. View Current Profile (Preview)");
            System.out.println("2. Add Skills");
            System.out.println("3. Add Projects");
            System.out.println("4. Add Certifications");
            System.out.println("5. CONFIRM & APPLY NOW");
            System.out.println("6. Cancel");
            System.out.print("Action: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: viewFullProfile(userId); break;
                    case 2: handleSkillManagement(userId); break;
                    case 3: handleAddProject(userId); break;
                    case 4: handleAddCertification(userId); break;
                    case 5:
                        if (applicationService.applyForJob(userId, jobId)) {
                            System.out.println("✅ Application submitted successfully!");
                        } else {
                            System.out.println("❌ You have already applied for this job.");
                        }
                        finished = true;
                        break;
                    case 6: finished = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Enter a number (1-6).");
            }
        }
    }

    private void handleViewNotifications(int userId) {
        List<Notifications> notifs = notificationService.getNotificationsForUser(userId);
        if (notifs.isEmpty()) {
            System.out.println("No notifications found.");
            return;
        }
        System.out.println("\n--- YOUR NOTIFICATIONS ---");
        for (Notifications n : notifs) {
            String status = n.isRead() ? "[READ]" : "[NEW]";
            System.out.println(status + " " + n.getMessage());
            notificationService.markAsRead(n.getNotificationId());
        }
    }

    private void handleResumeManagement(int userId) {
        int resumeId = resumeService.getResumeIdByUserId(userId);
        boolean back = false;
        while (!back) {
            System.out.println("\n--- RESUME MANAGEMENT ---");
            System.out.println("1. View Full Profile");
            System.out.println("2. Manage Personal Details");
            System.out.println("3. Manage Education");
            System.out.println("4. Manage Experience");
            System.out.println("5. Add Skill");
            System.out.println("6. Add Project");
            System.out.println("7. Add Certification");
            System.out.println("8. Back");
            System.out.print("Select: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: viewFullProfile(userId); break;
                    case 2: managePersonalDetails(userId); break;
                    case 3: manageEducation(resumeId, userId); break;
                    case 4: manageExperience(resumeId); break;
                    case 5: handleSkillManagement(userId); break;
                    case 6: handleAddProject(userId); break;
                    case 7: handleAddCertification(userId); break;
                    case 8: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input.");
            }
        }
    }

    private void viewFullProfile(int userId) {
        Resume resume = resumeService.getProfile(userId);
        if (resume == null) {
            System.out.println("No profile found. Please add personal details first.");
            return;
        }
        System.out.println("\n================ RESUME PREVIEW ================");
        List<String> skills = resumeSkillsService.getSkillNamesByResume(resume.getResumeId());
        System.out.println("SKILLS: " + (skills.isEmpty() ? "None added" : String.join(", ", skills)));

        List<Projects> projects = projectService.getProjectsByResume(resume.getResumeId());
        System.out.println("\nPROJECTS:");
        if (projects.isEmpty()) System.out.println("- None");
        for (Projects p : projects) System.out.println("• " + p.getTitle() + " | Stack: " + p.getTechStack());

        List<Certifications> certs = certificationService.getCertificationsByUser(userId);
        System.out.println("\nCERTIFICATIONS:");
        if (certs.isEmpty()) System.out.println("- None");
        for (Certifications c : certs) System.out.println("• " + c.getCertName() + " (" + c.getIssuingOrganization() + ")");
        System.out.println("================================================");
    }

    private void handleSkillManagement(int userId) {
        int resumeId = resumeService.getResumeIdByUserId(userId);
        System.out.print("Enter skill name: ");
        String skill = scanner.nextLine();
        int skillId = skillsService.getOrCreateSkill(skill);
        if (resumeSkillsService.addSkillToResume(resumeId, skillId)) {
            System.out.println("✅ Skill added.");
        } else {
            System.out.println("⚠️ Skill already exists.");
        }
    }

    private void handleAddProject(int userId) {
        int resumeId = resumeService.getResumeIdByUserId(userId);
        System.out.print("Project Title: "); String title = scanner.nextLine();
        System.out.print("Description: "); String desc = scanner.nextLine();
        System.out.print("Tech Stack: "); String tech = scanner.nextLine();
        if(projectService.addProject(resumeId, title, desc, tech)) System.out.println("✅ Project Added.");
    }

    private void handleAddCertification(int userId) {
        System.out.print("Cert Name: "); String name = scanner.nextLine();
        System.out.print("Org: "); String org = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): "); String date = scanner.nextLine();
        if(certificationService.addCertification(userId, name, org, date)) System.out.println("✅ Cert Added.");
    }

    private void handleViewApplications(int userId) {
        List<JobApplications> apps = applicationService.getApplicationsByUserId(userId);
        if (apps.isEmpty()) {
            System.out.println("No applications found.");
        } else {
            System.out.println("\n--- MY APPLICATIONS ---");
            System.out.printf("%-10s %-20s %-20s %-15s%n", "ID", "Job Title", "Company", "Status");
            for (JobApplications app : apps) {
                System.out.printf("%-10d %-20s %-20s %-15s%n", 
                    app.getJobId(), 
                    app.getJobTitle(),    // From the Join
                    app.getCompanyName(), // From the Join
                    app.getStatus());
            }
        }
    }

    public void displayEmployerDashboard(int userId) {
        while (true) {
            System.out.println("\n--- EMPLOYER DASHBOARD ---");
            System.out.println("1. Manage Company Profile");
            System.out.println("2. Post a Job");
            System.out.println("3. Manage Job Skill Requirements");
            System.out.println("4. Update Job");
            System.out.println("5. Delete a Job");
            System.out.println("6. Manage Received Applications (Shortlist/Reject)"); // NEW OPTION
            System.out.println("7. Logout");
            System.out.print("Select: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine()); 
                switch (choice) {
                    case 1: handleUpdateEmployerProfile(userId); break;
                    case 2: handlePostJob(userId); break; 
                    case 3: manageJobSkills(userId); break;
                    case 4: handleUpdateJob(userId); break;
                    case 5: handleDeleteJob(userId); break; 
                    case 6: handleManageApplications(userId); break; // NEW CASE
                    case 7: return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private void handleUpdateEmployerProfile(int userId) {
        EmployerProfile profile = employerService.getProfile(userId);
        
        if (profile == null) {
            System.out.println("\n--- Create Your Company Profile ---");
            profile = new EmployerProfile();
            profile.setUserId(userId);
            
            System.out.print("Company Name: "); profile.setCompanyName(scanner.nextLine());
            System.out.print("Industry: "); profile.setIndustry(scanner.nextLine());
            System.out.print("Location: "); profile.setLocation(scanner.nextLine());
            System.out.print("Website: "); profile.setWebsite(scanner.nextLine());
            System.out.print("Description: "); profile.setDescription(scanner.nextLine());

            if(employerService.updateProfile(profile)) { 
                System.out.println("✅ Profile created successfully!");
            }
        } else {
            boolean back = false;
            while (!back) {
                System.out.println("\n--- Company Profile: " + profile.getCompanyName() + " ---");
                System.out.println("Location: " + profile.getLocation());
                System.out.println("Industry: " + profile.getIndustry());
                System.out.println("------------------------------------");
                System.out.println("1. Update Name");
                System.out.println("2. Update Location");
                System.out.println("3. Update Industry");
                System.out.println("4. Back");
                System.out.print("Select: ");

                int subChoice = Integer.parseInt(scanner.nextLine());
                switch (subChoice) {
                    case 1:
                        System.out.print("New Name: ");
                        profile.setCompanyName(scanner.nextLine());
                        employerService.updateProfile(profile);
                        break;
                    case 2:
                        System.out.print("New Location: ");
                        profile.setLocation(scanner.nextLine());
                        employerService.updateProfile(profile);
                        break;
                    case 3:
                        System.out.print("New Industry: ");
                        profile.setIndustry(scanner.nextLine());
                        employerService.updateProfile(profile);
                        break;
                    case 4:
                        back = true;
                        break;
                }
            }
        }
    }
    private void managePersonalDetails(int userId) {
        JobSeekerProfile profile = profileService.getProfileByUserId(userId);
        if (profile == null) {
            JobSeekerProfile newProfile = new JobSeekerProfile();
            newProfile.setUserId(userId);
            System.out.print("Full Name: "); newProfile.setFullName(scanner.nextLine());
            System.out.print("Phone: "); newProfile.setPhone(scanner.nextLine());
            System.out.print("Location: "); newProfile.setLocation(scanner.nextLine());
            System.out.print("Exp (Years): "); 
            newProfile.setTotalExperience(Integer.parseInt(scanner.nextLine()));
            profileService.createProfile(newProfile);
        } else {
            System.out.println("Name: " + profile.getFullName() + " | Location: " + profile.getLocation());
        }
    }

    private void manageEducation(int resumeId, int userId) {
        List<Education> eduList = educationService.getEducationByUserId(userId);
        if (eduList != null) for (Education e : eduList) System.out.println("- " + e.getDegree());
        System.out.println("1. Add Education / 2. Back");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                Education edu = new Education(); edu.setResumeId(resumeId);
                System.out.print("Degree: "); edu.setDegree(scanner.nextLine());
                System.out.print("Institution: "); edu.setInstitution(scanner.nextLine());
                System.out.print("Start Year: "); edu.setStartYear(Integer.parseInt(scanner.nextLine()));
                System.out.print("End Year: "); edu.setEndYear(Integer.parseInt(scanner.nextLine()));
                educationService.addEducation(edu);
            }
        } catch (Exception e) { System.out.println("Error saving education."); }
    }

    private void manageExperience(int resumeId) {
        List<Experience> experiences = experienceService.getExperience(resumeId);
        if (experiences != null) for (Experience e : experiences) System.out.println("- " + e.getJobRole());
        System.out.println("1. Add Experience / 2. Back");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                Experience exp = new Experience(); exp.setResumeId(resumeId);
                System.out.print("Company: "); exp.setCompany(scanner.nextLine());
                System.out.print("Role: "); exp.setJobRole(scanner.nextLine());
                System.out.print("Start (YYYY-MM-DD): "); exp.setStartDate(java.sql.Date.valueOf(scanner.nextLine()));
                System.out.print("Description: "); exp.setDescription(scanner.nextLine());
                experienceService.addExperience(exp);
            }
        } catch (Exception e) { System.out.println("Error saving experience."); }
    }

   
    private void manageJobSkills(int employerId) {
        List<Jobs> myJobs = jobService.getJobsByEmployer(employerId);
        if (myJobs == null || myJobs.isEmpty()) {
            System.out.println("No jobs posted.");
            return;
        }
        
        System.out.println("\n--- SELECT A JOB ---");
        myJobs.forEach(j -> System.out.println(j.getJobId() + ": " + j.getTitle()));
        
        System.out.print("\nEnter Job ID: ");
        int jobId;
        try {
            jobId = Integer.parseInt(scanner.nextLine());
            // Validation: Check if this jobId actually belongs to this employer
            boolean ownsJob = myJobs.stream().anyMatch(j -> j.getJobId() == jobId);
            if (!ownsJob) {
                System.out.println("❌ You do not have permission for this Job ID.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
            return;
        }
        
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Job ID: " + jobId + " ---");
            System.out.println("1. View Current Skills");
            System.out.println("2. Add New Skill(s)");
            System.out.println("3. Back");
            System.out.print("Select: ");
            String sub = scanner.nextLine();
            
            switch (sub) {
                case "1":
                    List<Skills> skills = jobSkillsService.getSkillsByJobId(jobId);
                    if (skills == null || skills.isEmpty()) {
                        System.out.println("No skills listed for this job.");
                    } else {
                        System.out.print("Required Skills: ");
                        skills.forEach(s -> System.out.print("[" + s.getSkillName() + "] "));
                        System.out.println();
                    }
                    break;
                    
                case "2":
                    System.out.print("Enter Skill Name (use commas for multiple, e.g., Java, SQL): ");
                    String input = scanner.nextLine().trim();
                    
                    if (input.isEmpty()) {
                        System.out.println("❌ Skill name cannot be empty.");
                        break;
                    }

                    // Split by comma to allow batch adding
                    String[] skillNames = input.split(",");
                    for (String name : skillNames) {
                        String trimmedName = name.trim();
                        if (!trimmedName.isEmpty()) {
                            boolean success = jobSkillsService.addSkillToJob(jobId, trimmedName);
                            if (success) {
                                System.out.println("✅ Skill '" + trimmedName + "' added successfully!");
                            } else {
                                System.out.println("❌ Failed to add '" + trimmedName + "'. (Check if it exists or is already linked)");
                            }
                        }
                    }
                    break;
                    
                case "3":
                    back = true;
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    private void handleUpdateJob(int employerId) {
        List<Jobs> myJobs = jobService.getJobsByEmployer(employerId);
        myJobs.forEach(j -> System.out.println("ID: " + j.getJobId() + " | Title: " + j.getTitle()));
        System.out.print("\nJob ID: ");
        int jobId = Integer.parseInt(scanner.nextLine());
        Jobs job = jobService.getJobById(jobId);
        if (job != null && job.getEmployerId() == employerId) {
            System.out.print("New Title: "); job.setTitle(scanner.nextLine());
            jobService.updateJob(job);
        }
    }

    private void handleDeleteJob(int employerId) {
        System.out.print("Enter Job ID to close/delete: ");
        try {
            int jobId = Integer.parseInt(scanner.nextLine());
            
            // Call the service which now updates the status to 'CLOSED'
            if (jobService.deleteJob(jobId)) {
                System.out.println("✅ Job has been marked as CLOSED. Applications are preserved.");
            } else {
                System.out.println("❌ Failed to update job status.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
        }
    }

    private void handlePostJob(int employerId) {
        Jobs newJob = new Jobs();
        newJob.setEmployerId(employerId);
        System.out.print("Job Title: "); newJob.setTitle(scanner.nextLine());
        System.out.print("Min Salary: "); newJob.setSalaryMin(Double.parseDouble(scanner.nextLine()));
        System.out.print("Max Salary: "); newJob.setSalaryMax(Double.parseDouble(scanner.nextLine()));
        newJob.setStatus("OPEN");
        jobService.postJob(newJob);
    }
    private void handleManageApplications(int employerId) {
        // 1. Fetch applications for this employer's jobs
        List<JobApplications> apps = applicationService.getApplicationsForEmployer(employerId);

        if (apps == null || apps.isEmpty()) {
            System.out.println("\nNo applications found for your job postings.");
            return;
        }

        System.out.println("\n--- PENDING APPLICATIONS ---");
        System.out.printf("%-10s %-20s %-15s %-15s%n", "App ID", "Job Title", "Seeker ID", "Current Status");
        System.out.println("------------------------------------------------------------");
        for (JobApplications app : apps) {
            System.out.printf("%-10d %-20s %-15d %-15s%n", 
                app.getApplicationId(), app.getJobTitle(), app.getUserId(), app.getStatus());
        }

        System.out.print("\nEnter Application ID to update (or 0 to cancel): ");
        try {
            int appId = Integer.parseInt(scanner.nextLine());
            if (appId == 0) return;

            // Find the selected application in our list
            JobApplications selected = apps.stream()
                .filter(a -> a.getApplicationId() == appId)
                .findFirst()
                .orElse(null);

            if (selected == null) {
                System.out.println("❌ Invalid Application ID.");
                return;
            }

            System.out.println("Select Action: [1] Shortlist [2] Reject [3] Back");
            int action = Integer.parseInt(scanner.nextLine());

            String newStatus = "";
            if (action == 1) newStatus = "SHORTLISTED";
            else if (action == 2) newStatus = "REJECTED";
            else return;

            // This calls the service which updates DB AND sends the notification
            boolean success = applicationService.updateApplicationStatus(
                appId, 
                newStatus, 
                selected.getUserId(), 
                selected.getJobTitle()
            );

            if (success) {
                System.out.println("✅ Application " + newStatus + " successfully. Notification sent.");
            } else {
                System.out.println("❌ Failed to update status.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
        }
    }
}