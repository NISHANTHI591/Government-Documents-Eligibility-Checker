
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.concurrent.*;

interface EligibilityCheckable {
    EligibilityResult checkEligibility(User user);
}

interface DocumentVerifiable {
    DocumentCheckResult checkDocuments(List<Document> requiredDocuments);
}


class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InvalidIncomeException extends Exception {
    public InvalidIncomeException(String message) {
        super(message);
    }
}

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class SchemeNotFoundException extends Exception {
    public SchemeNotFoundException(String message) {
        super(message);
    }
}

class MissingProfileException extends Exception {
    public MissingProfileException(String message) {
        super(message);
    }
}

class InvalidSchemeException extends Exception {
    public InvalidSchemeException(String message) {
        super(message);
    }
}


class Repository<T> {
    private final ArrayList<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void remove(T item) {
        items.remove(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}


class User {
    private String name;
    private String gender;
    private String category;
    private String state;
    private int age;
    private double income;
    private boolean student;
    private String educationLevel;
    private String course;
    private int yearOfStudy;
    private double marks;
    private String occupation;
    private boolean disabled;
    private double disabilityPercentage;
    private boolean farmer;
    private final Set<String> availableDocuments = new LinkedHashSet<>();

    // Constructor 1
    public User() {
        this.name = "Unknown";
        this.gender = "Other";
        this.category = "General";
        this.state = "";
        this.age = 18;
        this.income = 0;
        this.student = false;
        this.educationLevel = "Not Specified";
        this.course = "";
        this.yearOfStudy = 0;
        this.marks = 0;
        this.occupation = "Not Specified";
        this.disabled = false;
        this.disabilityPercentage = 0;
        this.farmer = false;
    }

    // Constructor 2
    public User(String name, int age) throws InvalidAgeException {
        validateAge(age);
        this.name = name;
        this.age = age;
        this.gender = "Other";
        this.category = "General";
        this.state = "";
        this.income = 0;
        this.student = false;
        this.educationLevel = "Not Specified";
        this.course = "";
        this.yearOfStudy = 0;
        this.marks = 0;
        this.occupation = "Not Specified";
        this.disabled = false;
        this.disabilityPercentage = 0;
        this.farmer = false;
    }

    // Constructor 3 - retained logically from the original project
    public User(String name, String gender, String category,
                String state, int age, double income, boolean student)
            throws InvalidAgeException, InvalidIncomeException {
        validateAge(age);
        validateIncome(income);
        this.name = name;
        this.gender = gender;
        this.category = category;
        this.state = state;
        this.age = age;
        this.income = income;
        this.student = student;
        this.educationLevel = "Not Specified";
        this.course = "";
        this.yearOfStudy = 0;
        this.marks = 0;
        this.occupation = "Not Specified";
        this.disabled = false;
        this.disabilityPercentage = 0;
        this.farmer = false;
    }

    // Full constructor
    public User(String name, String gender, String category, String state,
                int age, double income, boolean student,
                String educationLevel, String course, int yearOfStudy,
                double marks, String occupation, boolean disabled,
                double disabilityPercentage, boolean farmer)
            throws InvalidAgeException, InvalidIncomeException, InvalidMarksException {
        validateAge(age);
        validateIncome(income);
        validateMarks(marks);

        this.name = name;
        this.gender = gender;
        this.category = category;
        this.state = state;
        this.age = age;
        this.income = income;
        this.student = student;
        this.educationLevel = educationLevel;
        this.course = course;
        this.yearOfStudy = yearOfStudy;
        this.marks = marks;
        this.occupation = occupation;
        this.disabled = disabled;
        this.disabilityPercentage = disabilityPercentage;
        this.farmer = farmer;
    }

    private static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            throw new InvalidAgeException("Age must be between 0 and 120.");
        }
    }

    private static void validateIncome(double income) throws InvalidIncomeException {
        if (income < 0) {
            throw new InvalidIncomeException("Annual income cannot be negative.");
        }
    }

    private static void validateMarks(double marks) throws InvalidMarksException {
        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException("Marks/percentage must be between 0 and 100.");
        }
    }

    public void validate() throws InvalidAgeException, InvalidIncomeException, InvalidMarksException {
        validateAge(age);
        validateIncome(income);
        validateMarks(marks);
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty.");
        }
        if (disabled && (disabilityPercentage < 0 || disabilityPercentage > 100)) {
            throw new IllegalArgumentException("Disability percentage must be between 0 and 100.");
        }
    }

    // Getters and setters - encapsulation
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public int getAge() { return age; }
    public void setAge(int age) throws InvalidAgeException {
        validateAge(age);
        this.age = age;
    }

    public double getIncome() { return income; }
    public void setIncome(double income) throws InvalidIncomeException {
        validateIncome(income);
        this.income = income;
    }

    public boolean isStudent() { return student; }
    public void setStudent(boolean student) { this.student = student; }

    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) throws InvalidMarksException {
        validateMarks(marks);
        this.marks = marks;
    }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public boolean isDisabled() { return disabled; }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        if (!disabled) this.disabilityPercentage = 0;
    }

    public double getDisabilityPercentage() { return disabilityPercentage; }
    public void setDisabilityPercentage(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Disability percentage must be between 0 and 100.");
        }
        this.disabilityPercentage = percentage;
    }

    public boolean isFarmer() { return farmer; }
    public void setFarmer(boolean farmer) { this.farmer = farmer; }

    public Set<String> getAvailableDocuments() {
        return new LinkedHashSet<>(availableDocuments);
    }

    public void setAvailableDocuments(Set<String> documents) {
        availableDocuments.clear();
        if (documents != null) {
            for (String document : documents) {
                if (document != null && !document.trim().isEmpty()) {
                    availableDocuments.add(document.trim());
                }
            }
        }
    }

    public void addAvailableDocument(String document) {
        if (document != null && !document.trim().isEmpty()) {
            availableDocuments.add(document.trim());
        }
    }

    public void removeAvailableDocument(String document) {
        if (document != null) availableDocuments.remove(document);
    }

    public void displayDetails() {
        System.out.println(getProfileSummary());
    }

    public String getProfileSummary() {
        return "Name: " + name
                + "\nAge: " + age
                + "\nGender: " + gender
                + "\nCategory: " + category
                + "\nState: " + state
                + "\nAnnual Income: Rs. " + String.format("%.2f", income)
                + "\nStudent: " + (student ? "Yes" : "No")
                + "\nEducation: " + educationLevel
                + "\nCourse/Stream: " + course
                + "\nYear of Study: " + (yearOfStudy == 0 ? "Not applicable" : yearOfStudy)
                + "\nMarks/Percentage: " + String.format("%.2f", marks)
                + "\nOccupation: " + occupation
                + "\nDisability: " + (disabled ? "Yes (" + disabilityPercentage + "%)" : "No")
                + "\nFarmer: " + (farmer ? "Yes" : "No");
    }
}


class Document {
    private String documentName;
    private boolean required;
    private boolean available;
    private boolean verified;

    public Document(String documentName, boolean required) {
        this(documentName, required, false, false);
    }

    public Document(String documentName, boolean required,
                    boolean available, boolean verified) {
        this.documentName = documentName;
        this.required = required;
        this.available = available;
        this.verified = verified;
    }

    public String getDocumentName() { return documentName; }
    public boolean isRequired() { return required; }
    public boolean isAvailable() { return available; }
    public boolean isVerified() { return verified; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getStatusLine() {
        if (available && verified) return "✓ " + documentName + " (Available & Verified)";
        if (available) return "✓ " + documentName + " (Available - Verification Pending)";
        return "✗ " + documentName + " (Missing)";
    }
}

class DocumentCheckResult {
    private final ArrayList<Document> requiredDocuments;
    private final ArrayList<Document> missingDocuments;

    public DocumentCheckResult(List<Document> requiredDocuments) {
        this.requiredDocuments = new ArrayList<>(requiredDocuments);
        this.missingDocuments = new ArrayList<>();
        for (Document document : requiredDocuments) {
            if (document.isRequired() && !document.isAvailable()) {
                missingDocuments.add(document);
            }
        }
    }

    public ArrayList<Document> getRequiredDocuments() {
        return new ArrayList<>(requiredDocuments);
    }

    public ArrayList<Document> getMissingDocuments() {
        return new ArrayList<>(missingDocuments);
    }

    public boolean isComplete() {
        return missingDocuments.isEmpty();
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("DOCUMENT STATUS\n\n");
        for (Document document : requiredDocuments) {
            sb.append(document.getStatusLine()).append("\n");
        }

        if (missingDocuments.isEmpty()) {
            sb.append("\nDocument Status: COMPLETE\n");
            sb.append("All required documents are available.");
        } else {
            sb.append("\nMissing Documents:\n");
            for (Document document : missingDocuments) {
                sb.append("✗ ").append(document.getDocumentName()).append("\n");
            }
            sb.append("\nDocument Status: INCOMPLETE\n");
            sb.append("Action Required: Obtain/upload the missing document(s) before applying.");
        }
        return sb.toString();
    }
}

enum EligibilityStatus {
    ELIGIBLE,
    ALMOST_ELIGIBLE,
    NOT_ELIGIBLE
}

class EligibilityResult {
    private final EligibilityStatus status;
    private final Scheme scheme;
    private final ArrayList<String> satisfiedRequirements;
    private final ArrayList<String> missingRequirements;
    private final ArrayList<Document> missingDocuments;
    private final String benefit;
    private final String recommendedAction;

    public EligibilityResult(EligibilityStatus status,
                             Scheme scheme,
                             List<String> satisfiedRequirements,
                             List<String> missingRequirements,
                             List<Document> missingDocuments,
                             String benefit,
                             String recommendedAction) {
        this.status = status;
        this.scheme = scheme;
        this.satisfiedRequirements = new ArrayList<>(satisfiedRequirements);
        this.missingRequirements = new ArrayList<>(missingRequirements);
        this.missingDocuments = new ArrayList<>(missingDocuments);
        this.benefit = benefit;
        this.recommendedAction = recommendedAction;
    }

    public EligibilityStatus getStatus() { return status; }
    public Scheme getScheme() { return scheme; }
    public ArrayList<String> getSatisfiedRequirements() {
        return new ArrayList<>(satisfiedRequirements);
    }
    public ArrayList<String> getMissingRequirements() {
        return new ArrayList<>(missingRequirements);
    }
    public ArrayList<Document> getMissingDocuments() {
        return new ArrayList<>(missingDocuments);
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("ELIGIBILITY STATUS: ")
                .append(status.toString().replace('_', ' '))
                .append("\n\n");
        sb.append("Scheme: ").append(scheme.getName()).append("\n");
        sb.append("Benefit: ").append(benefit).append("\n\n");

        sb.append("You satisfy:\n");
        if (satisfiedRequirements.isEmpty()) {
            sb.append("- No eligibility conditions have been satisfied.\n");
        } else {
            for (String item : satisfiedRequirements) {
                sb.append("✓ ").append(item).append("\n");
            }
        }

        if (!missingRequirements.isEmpty()) {
            sb.append("\nMissing / Unmet Requirements:\n");
            for (String item : missingRequirements) {
                sb.append("✗ ").append(item).append("\n");
            }
        }

        if (!missingDocuments.isEmpty()) {
            sb.append("\nMissing Documents:\n");
            for (Document document : missingDocuments) {
                sb.append("✗ ").append(document.getDocumentName()).append("\n");
            }
        }

        sb.append("\nAction:\n").append(recommendedAction).append("\n");

        if (status == EligibilityStatus.ELIGIBLE) {
            sb.append("\nApplication Information:\n")
                    .append(scheme.getApplicationInformation());
        }
        return sb.toString();
    }
}

abstract class Scheme implements EligibilityCheckable, DocumentVerifiable {
    private static int schemeCount = 0;

    private final String id;
    private String name;
    private String type;
    private String schemeCategory;
    private String description;
    private String benefit;

    private String gender;
    private String category;
    private String state;

    private int minAge;
    private int maxAge;
    private double maxIncome;

    private boolean studentRequired;
    private double minMarks;
    private String educationRequirement;
    private String occupationRequirement;

    private boolean disabilityRequired;
    private double minDisabilityPercentage;
    private boolean farmerRequired;

    private final ArrayList<Document> requiredDocuments;
    private String applicationMethod;
    private String applicationInformation;
    private String applicationWebsite;

    protected Scheme(String id, String name, String type,
                     String description, String benefit,
                     String gender, String category, String state,
                     int minAge, int maxAge, double maxIncome,
                     boolean studentRequired, double minMarks,
                     String educationRequirement, String occupationRequirement,
                     boolean disabilityRequired, double minDisabilityPercentage,
                     boolean farmerRequired, List<Document> requiredDocuments,
                     String applicationMethod, String applicationInformation,
                     String applicationWebsite) throws InvalidSchemeException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidSchemeException("Scheme ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidSchemeException("Scheme name cannot be empty.");
        }
        if (minAge < 0 || maxAge < minAge || maxAge > 120) {
            throw new InvalidSchemeException("Invalid age range.");
        }
        if (maxIncome < 0) {
            throw new InvalidSchemeException("Maximum income cannot be negative.");
        }
        if (minMarks < 0 || minMarks > 100) {
            throw new InvalidSchemeException("Minimum marks must be between 0 and 100.");
        }
        if (minDisabilityPercentage < 0 || minDisabilityPercentage > 100) {
            throw new InvalidSchemeException("Disability percentage must be between 0 and 100.");
        }

        this.id = id.trim();
        this.name = name.trim();
        this.type = type;
        this.schemeCategory = type.equalsIgnoreCase("Scholarship") ? "Education" : "Welfare";
        this.description = description;
        this.benefit = benefit;
        this.gender = gender;
        this.category = category;
        this.state = state;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.maxIncome = maxIncome;
        this.studentRequired = studentRequired;
        this.minMarks = minMarks;
        this.educationRequirement = educationRequirement;
        this.occupationRequirement = occupationRequirement;
        this.disabilityRequired = disabilityRequired;
        this.minDisabilityPercentage = minDisabilityPercentage;
        this.farmerRequired = farmerRequired;
        this.requiredDocuments = new ArrayList<>();
        if (requiredDocuments != null) {
            for (Document d : requiredDocuments) {
                this.requiredDocuments.add(new Document(
                        d.getDocumentName(), d.isRequired(),
                        d.isAvailable(), d.isVerified()));
            }
        }
        this.applicationMethod = applicationMethod;
        this.applicationInformation = applicationInformation;
        this.applicationWebsite = applicationWebsite;
        schemeCount++;
    }

    public abstract String getSchemeType();

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getSchemeCategory() { return schemeCategory; }
    public String getDescription() { return description; }
    public String getBenefit() { return benefit; }
    public String getGender() { return gender; }
    public String getCategory() { return category; }
    public String getState() { return state; }
    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public double getMaxIncome() { return maxIncome; }
    public boolean isStudentRequired() { return studentRequired; }
    public double getMinMarks() { return minMarks; }
    public String getEducationRequirement() { return educationRequirement; }
    public String getOccupationRequirement() { return occupationRequirement; }
    public boolean isDisabilityRequired() { return disabilityRequired; }
    public double getMinDisabilityPercentage() { return minDisabilityPercentage; }
    public boolean isFarmerRequired() { return farmerRequired; }
    public String getApplicationMethod() { return applicationMethod; }
    public String getApplicationInformation() { return applicationInformation; }
    public String getApplicationWebsite() { return applicationWebsite; }

    public static int getSchemeCount() { return schemeCount; }

    public List<Document> getRequiredDocuments() {
        ArrayList<Document> copy = new ArrayList<>();
        for (Document d : requiredDocuments) {
            copy.add(new Document(d.getDocumentName(), d.isRequired(),
                    d.isAvailable(), d.isVerified()));
        }
        return copy;
    }

    public void setName(String name) { this.name = name; }
    public void setSchemeCategory(String value) { this.schemeCategory = value; }
    public void setDescription(String description) { this.description = description; }
    public void setBenefit(String benefit) { this.benefit = benefit; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCategory(String category) { this.category = category; }
    public void setState(String state) { this.state = state; }
    public void setMinAge(int minAge) { this.minAge = minAge; }
    public void setMaxAge(int maxAge) { this.maxAge = maxAge; }
    public void setMaxIncome(double maxIncome) { this.maxIncome = maxIncome; }
    public void setStudentRequired(boolean value) { this.studentRequired = value; }
    public void setMinMarks(double value) { this.minMarks = value; }
    public void setEducationRequirement(String value) { this.educationRequirement = value; }
    public void setOccupationRequirement(String value) { this.occupationRequirement = value; }
    public void setDisabilityRequired(boolean value) { this.disabilityRequired = value; }
    public void setMinDisabilityPercentage(double value) { this.minDisabilityPercentage = value; }
    public void setFarmerRequired(boolean value) { this.farmerRequired = value; }
    public void setApplicationMethod(String value) { this.applicationMethod = value; }
    public void setApplicationInformation(String value) { this.applicationInformation = value; }
    public void setApplicationWebsite(String value) { this.applicationWebsite = value; }

    public void replaceDocuments(List<Document> documents) {
        requiredDocuments.clear();
        if (documents != null) {
            for (Document d : documents) {
                requiredDocuments.add(new Document(d.getDocumentName(),
                        d.isRequired(), d.isAvailable(), d.isVerified()));
            }
        }
    }

    // Main eligibility engine
    @Override
    public EligibilityResult checkEligibility(User user) {
        ArrayList<String> satisfied = new ArrayList<>();
        ArrayList<String> missing = new ArrayList<>();

        if (user.getAge() >= minAge && user.getAge() <= maxAge) {
            satisfied.add("Age requirement (" + minAge + "-" + maxAge + ")");
        } else {
            missing.add("Age must be between " + minAge + " and " + maxAge
                    + ". Your age: " + user.getAge());
        }

        if (gender.equalsIgnoreCase("Any") ||
                gender.equalsIgnoreCase(user.getGender())) {
            satisfied.add("Gender requirement");
        } else {
            missing.add("Required gender: " + gender
                    + ". Your gender: " + user.getGender());
        }

        if (category.equalsIgnoreCase("Any") ||
                category.equalsIgnoreCase(user.getCategory())) {
            satisfied.add("Category requirement");
        } else {
            missing.add("Required category: " + category
                    + ". Your category: " + user.getCategory());
        }

        if (state.equalsIgnoreCase("Any") ||
                state.equalsIgnoreCase(user.getState())) {
            satisfied.add("State requirement");
        } else {
            missing.add("Required state: " + state
                    + ". Your state: " + user.getState());
        }

        if (user.getIncome() <= maxIncome) {
            satisfied.add("Income requirement (maximum Rs. "
                    + String.format("%.0f", maxIncome) + ")");
        } else {
            missing.add("Maximum income allowed: Rs. "
                    + String.format("%.0f", maxIncome)
                    + ". Your income: Rs. "
                    + String.format("%.0f", user.getIncome()));
        }

        if (!studentRequired || user.isStudent()) {
            satisfied.add(studentRequired ? "Student requirement" : "Student status not required");
        } else {
            missing.add("Applicant must be a student. Current status: Not a student");
        }

        if (minMarks <= 0 || user.getMarks() >= minMarks) {
            satisfied.add(minMarks <= 0 ? "Marks requirement not specified"
                    : "Minimum marks requirement (" + minMarks + "%)");
        } else {
            missing.add("Minimum marks required: " + minMarks
                    + "%. Your marks: " + user.getMarks() + "%");
        }

        if (educationRequirement.equalsIgnoreCase("Any") ||
                educationRequirement.equalsIgnoreCase("Not Specified") ||
                user.getEducationLevel().toLowerCase()
                        .contains(educationRequirement.toLowerCase())) {
            satisfied.add("Education requirement");
        } else {
            missing.add("Required education: " + educationRequirement
                    + ". Your education: " + user.getEducationLevel());
        }

        if (occupationRequirement.equalsIgnoreCase("Any") ||
                occupationRequirement.equalsIgnoreCase("Not Specified") ||
                user.getOccupation().equalsIgnoreCase(occupationRequirement)) {
            satisfied.add("Occupation requirement");
        } else {
            missing.add("Required occupation: " + occupationRequirement
                    + ". Your occupation: " + user.getOccupation());
        }

        if (!disabilityRequired ||
                (user.isDisabled() &&
                        user.getDisabilityPercentage() >= minDisabilityPercentage)) {
            if (disabilityRequired) {
                satisfied.add("Disability requirement (minimum "
                        + minDisabilityPercentage + "%)");
            } else {
                satisfied.add("Disability requirement not applicable");
            }
        } else {
            if (!user.isDisabled()) {
                missing.add("Disability status is required for this scheme.");
            } else {
                missing.add("Minimum disability percentage required: "
                        + minDisabilityPercentage + "%. Your percentage: "
                        + user.getDisabilityPercentage() + "%");
            }
        }

        if (!farmerRequired || user.isFarmer()) {
            satisfied.add(farmerRequired ? "Farmer requirement" : "Farmer status not required");
        } else {
            missing.add("Applicant must be a farmer/farmer-family member.");
        }

        // Document availability belongs to the USER, not to an individual Scheme.
        DocumentCheckResult documentResult = checkDocumentsForUser(user);

        EligibilityStatus status;
        String action;

        // Major eligibility failures produce NOT ELIGIBLE.
        int majorFailures = 0;
        for (String reason : missing) {
            String lower = reason.toLowerCase();
            if (lower.startsWith("age")
                    || lower.startsWith("maximum income")
                    || lower.startsWith("required gender")
                    || lower.startsWith("required category")
                    || lower.startsWith("required state")
                    || lower.startsWith("applicant must be")
                    || lower.startsWith("disability status")
                    || lower.startsWith("minimum disability")) {
                majorFailures++;
            }
        }

        if (missing.isEmpty() && documentResult.isComplete()) {
            status = EligibilityStatus.ELIGIBLE;
            action = "You satisfy the sample eligibility criteria and have all required documents. "
                    + "You may proceed with the application using the information below.";
        } else if (majorFailures > 0) {
            status = EligibilityStatus.NOT_ELIGIBLE;
            action = "You currently fail one or more major eligibility conditions. "
                    + "Review the exact reasons above and consider another scheme.";
        } else {
            status = EligibilityStatus.ALMOST_ELIGIBLE;
            action = "You satisfy most eligibility conditions. Complete the missing requirements "
                    + "and/or obtain the missing documents before applying.";
        }

        return new EligibilityResult(status, this, satisfied, missing,
                documentResult.getMissingDocuments(), benefit, action);
    }

    @Override
    public DocumentCheckResult checkDocuments(List<Document> documents) {
        return new DocumentCheckResult(documents);
    }

    public DocumentCheckResult checkDocumentsForUser(User user) {
        ArrayList<Document> userStatus = new ArrayList<>();
        Set<String> available = user == null
                ? new LinkedHashSet<String>() : user.getAvailableDocuments();

        for (Document required : requiredDocuments) {
            boolean hasDocument = available.contains(required.getDocumentName());
            userStatus.add(new Document(required.getDocumentName(),
                    required.isRequired(), hasDocument, false));
        }
        return checkDocuments(userStatus);
    }

    // Method overloading in Scheme
    public void updateApplication(String method, String information) {
        this.applicationMethod = method;
        this.applicationInformation = information;
    }

    public void updateApplication(String method, String information, String website) {
        this.applicationMethod = method;
        this.applicationInformation = information;
        this.applicationWebsite = website;
    }

    // Method intended for runtime polymorphism
    public String getDetailsForUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("SCHEME DETAILS\n\n");
        sb.append("Scheme Name: ").append(name).append("\n");
        sb.append("Type: ").append(getSchemeType()).append("\n");
        sb.append("Category: ").append(schemeCategory).append("\n\n");
        sb.append("Description:\n").append(description).append("\n\n");
        sb.append("Benefit:\n").append(benefit).append("\n\n");
        sb.append("Eligibility Conditions:\n");
        sb.append("- Age: ").append(minAge).append(" - ").append(maxAge).append("\n");
        sb.append("- Maximum Income: Rs. ").append(String.format("%.0f", maxIncome)).append("\n");
        sb.append("- Gender: ").append(gender).append("\n");
        sb.append("- Beneficiary Category: ").append(category).append("\n");
        sb.append("- State: ").append(state).append("\n");
        sb.append("- Student Required: ").append(studentRequired ? "Yes" : "No").append("\n");
        sb.append("- Minimum Marks: ").append(minMarks <= 0 ? "Not specified" : minMarks + "%").append("\n");
        sb.append("- Education: ").append(educationRequirement).append("\n");
        sb.append("- Occupation: ").append(occupationRequirement).append("\n");
        sb.append("- Disability Required: ").append(disabilityRequired ? "Yes (" + minDisabilityPercentage + "% minimum)" : "No").append("\n");
        sb.append("- Farmer Required: ").append(farmerRequired ? "Yes" : "No").append("\n\n");
        sb.append("Required Documents:\n");
        if (requiredDocuments.isEmpty()) sb.append("- No documents specified\n");
        else for (Document d : requiredDocuments) sb.append("- ").append(d.getDocumentName()).append(d.isRequired() ? " (Required)" : " (Optional)").append("\n");
        sb.append("\nApplication Method:\n").append(applicationMethod).append("\n");
        sb.append("Application Information:\n").append(applicationInformation).append("\n");
        sb.append("Application Website/Information:\n").append(applicationWebsite).append("\n");
        return sb.toString();
    }

    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("SCHEME DETAILS\n\n");
        sb.append("Scheme ID: ").append(id).append("\n");
        sb.append("Scheme Name: ").append(name).append("\n");
        sb.append("Type: ").append(getSchemeType()).append("\n");
        sb.append("Scheme Category: ").append(schemeCategory).append("\n\n");
        sb.append("Description:\n").append(description).append("\n\n");
        sb.append("Benefit:\n").append(benefit).append("\n\n");
        sb.append("Eligibility Conditions:\n");
        sb.append("- Age: ").append(minAge).append(" - ").append(maxAge).append("\n");
        sb.append("- Maximum Income: Rs. ").append(String.format("%.0f", maxIncome)).append("\n");
        sb.append("- Gender: ").append(gender).append("\n");
        sb.append("- Category: ").append(category).append("\n");
        sb.append("- State: ").append(state).append("\n");
        sb.append("- Student Required: ").append(studentRequired ? "Yes" : "No").append("\n");
        sb.append("- Minimum Marks: ").append(minMarks <= 0 ? "Not specified" : minMarks + "%").append("\n");
        sb.append("- Education: ").append(educationRequirement).append("\n");
        sb.append("- Occupation: ").append(occupationRequirement).append("\n");
        sb.append("- Disability Required: ").append(disabilityRequired ? "Yes (" + minDisabilityPercentage + "% minimum)" : "No").append("\n");
        sb.append("- Farmer Required: ").append(farmerRequired ? "Yes" : "No").append("\n\n");

        sb.append("Required Documents:\n");
        if (requiredDocuments.isEmpty()) {
            sb.append("- No documents specified\n");
        } else {
            for (Document d : requiredDocuments) {
                sb.append("- ").append(d.getDocumentName())
                        .append(d.isRequired() ? " (Required)" : " (Optional)").append("\n");
            }
        }

        sb.append("\nApplication Method:\n").append(applicationMethod).append("\n");
        sb.append("Application Information:\n").append(applicationInformation).append("\n");
        sb.append("Application Website/Information:\n").append(applicationWebsite).append("\n");
        return sb.toString();
    }
}

class Scholarship extends Scheme {
    public Scholarship(String id, String name, String description, String benefit,
                       String gender, String category, String state,
                       int minAge, int maxAge, double maxIncome,
                       boolean studentRequired, double minMarks,
                       String educationRequirement, String occupationRequirement,
                       boolean disabilityRequired, double minDisabilityPercentage,
                       boolean farmerRequired, List<Document> documents,
                       String applicationMethod, String applicationInformation,
                       String applicationWebsite) throws InvalidSchemeException {
        super(id, name, "Scholarship", description, benefit,
                gender, category, state, minAge, maxAge, maxIncome,
                studentRequired, minMarks, educationRequirement, occupationRequirement,
                disabilityRequired, minDisabilityPercentage, farmerRequired,
                documents, applicationMethod, applicationInformation, applicationWebsite);
    }

    @Override
    public String getSchemeType() {
        return "Scholarship";
    }

    @Override
    public String getDetails() {
        return "SCHOLARSHIP\n\n" + super.getDetails();
    }
}

class WelfareScheme extends Scheme {
    public WelfareScheme(String id, String name, String description, String benefit,
                         String gender, String category, String state,
                         int minAge, int maxAge, double maxIncome,
                         boolean studentRequired, double minMarks,
                         String educationRequirement, String occupationRequirement,
                         boolean disabilityRequired, double minDisabilityPercentage,
                         boolean farmerRequired, List<Document> documents,
                         String applicationMethod, String applicationInformation,
                         String applicationWebsite) throws InvalidSchemeException {
        super(id, name, "Welfare Scheme", description, benefit,
                gender, category, state, minAge, maxAge, maxIncome,
                studentRequired, minMarks, educationRequirement, occupationRequirement,
                disabilityRequired, minDisabilityPercentage, farmerRequired,
                documents, applicationMethod, applicationInformation, applicationWebsite);
    }

    @Override
    public String getSchemeType() {
        return "Welfare Scheme";
    }

    @Override
    public String getDetails() {
        return "WELFARE SCHEME\n\n" + super.getDetails();
    }
}

class Recommendation {
    private final Scheme scheme;
    private final int score;
    private final ArrayList<String> matchingReasons;

    public Recommendation(Scheme scheme, int score, List<String> matchingReasons) {
        this.scheme = scheme;
        this.score = score;
        this.matchingReasons = new ArrayList<>(matchingReasons);
    }

    public Scheme getScheme() { return scheme; }
    public int getScore() { return score; }
    public List<String> getMatchingReasons() {
        return new ArrayList<>(matchingReasons);
    }
}

class RecommendationService {
    public List<Recommendation> recommend(User user, List<Scheme> schemes) {
        ArrayList<Recommendation> recommendations = new ArrayList<>();

        for (Scheme scheme : schemes) {
            EligibilityResult result = scheme.checkEligibility(user);
            int eligibilityMatches = result.getSatisfiedRequirements().size();
            int totalEligibility = eligibilityMatches + result.getMissingRequirements().size();
            int documentTotal = scheme.getRequiredDocuments().size();
            int documentAvailable = documentTotal - result.getMissingDocuments().size();

            int score = eligibilityMatches + documentAvailable;
            ArrayList<String> matches = new ArrayList<>(result.getSatisfiedRequirements());
            if (documentTotal > 0) {
                matches.add(documentAvailable + "/" + documentTotal + " required documents available");
            }

            // Recommendations are now driven by the same final eligibility engine.
            // Major failures are never presented as strong recommendations.
            if (result.getStatus() != EligibilityStatus.NOT_ELIGIBLE) {
                recommendations.add(new Recommendation(scheme, score, matches));
            }
        }

        Collections.sort(recommendations, new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation a, Recommendation b) {
                return Integer.compare(b.getScore(), a.getScore());
            }
        });

        return recommendations;
    }
}

public class GovernmentSchemePortal extends JFrame {

    private final Repository<Scheme> schemeRepository = new Repository<>();
    private final Repository<User> userRepository = new Repository<>();
    private final RecommendationService recommendationService =
            new RecommendationService();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private User currentUser;

    // Existing admin password preserved.
    private static final String ADMIN_PASSWORD = "1122";
    private static final int MAX_THREADS = 3;

        private void setSampleSchemeCategories() {
        setSchemeCategory("S101", "Education");
        setSchemeCategory("S102", "Women & Girls");
        setSchemeCategory("S103", "Education");
        setSchemeCategory("S104", "Education");
        setSchemeCategory("S105", "Education");
        setSchemeCategory("S106", "Women & Girls");
        setSchemeCategory("S107", "Senior Citizens");
        setSchemeCategory("S108", "Financial Assistance");
        setSchemeCategory("S109", "Farmers & Agriculture");
        setSchemeCategory("S110", "Education");
    }

    private void setSchemeCategory(String id, String category) {
        for (Scheme scheme : schemeRepository.getAll()) {
            if (scheme.getId().equalsIgnoreCase(id)) {
                scheme.setSchemeCategory(category);
                return;
            }
        }
    }

    public GovernmentSchemePortal() {
        setTitle("Government Scheme Eligibility and Document Checker");
        setSize(1120, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadSampleSchemes();

        mainPanel.add(createHomePanel(), "HOME");
        JPanel userPanel = createUserPanel();
        userPanel.setName("USER");
        mainPanel.add(userPanel, "USER");
        JPanel adminPanel = createAdminPanel();
        adminPanel.setName("ADMIN");
        mainPanel.add(adminPanel, "ADMIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }


    private void loadSampleSchemes() {
        try {
            schemeRepository.add(new Scholarship(
                    "S101", "Merit Student Scholarship",
                    "Financial support for students demonstrating academic merit.",
                    "Up to Rs. 50,000 per year.",
                    "Any", "Any", "Any",
                    16, 25, 300000,
                    true, 75,
                    "Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Mark Sheet", "Bonafide Certificate"),
                    "Online application",
                    "Apply through the scholarship portal during the notified application period.",
                    "Official scholarship portal / institution notice"
            ));

            schemeRepository.add(new Scholarship(
                    "S102", "Girl Child Education Support",
                    "Educational assistance for eligible female students.",
                    "Up to Rs. 40,000 per year.",
                    "Female", "Any", "Any",
                    10, 25, 250000,
                    true, 60,
                    "School / Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Mark Sheet", "Income Certificate"),
                    "Online application",
                    "Submit the application with the required certificates.",
                    "Official education department / scholarship portal"
            ));
            schemeRepository.getAll().get(schemeRepository.size() - 1).setSchemeCategory("Women & Girls");

            schemeRepository.add(new Scholarship(
                    "S103", "Backward Class Education Aid",
                    "Education assistance for students belonging to the specified category.",
                    "Up to Rs. 35,000 per year.",
                    "Any", "OBC", "Any",
                    16, 30, 200000,
                    true, 60,
                    "Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Community Certificate",
                            "Income Certificate", "Mark Sheet", "Bonafide Certificate"),
                    "Online application",
                    "Apply through the notified education welfare portal.",
                    "Official Backward Class Welfare portal"
            ));

            schemeRepository.add(new Scholarship(
                    "S104", "SC Student Financial Aid",
                    "Financial assistance for eligible SC students pursuing education.",
                    "Up to Rs. 45,000 per year.",
                    "Any", "SC", "Any",
                    16, 30, 250000,
                    true, 50,
                    "Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Community Certificate",
                            "Income Certificate", "Mark Sheet"),
                    "Online application",
                    "Submit the required documents through the relevant welfare portal.",
                    "Official Social Welfare / Education portal"
            ));

            schemeRepository.add(new Scholarship(
                    "S105", "ST Higher Education Support",
                    "Higher education support for eligible ST students.",
                    "Up to Rs. 45,000 per year.",
                    "Any", "ST", "Any",
                    17, 30, 250000,
                    true, 50,
                    "Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Community Certificate",
                            "Income Certificate", "Mark Sheet"),
                    "Online application",
                    "Apply through the relevant welfare scholarship portal.",
                    "Official Tribal Welfare / Education portal"
            ));

            schemeRepository.add(new WelfareScheme(
                    "S106", "Women Empowerment Support",
                    "Support programme for eligible women.",
                    "Financial assistance up to Rs. 30,000.",
                    "Female", "Any", "Any",
                    18, 60, 300000,
                    false, 0,
                    "Any", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Income Certificate"),
                    "Online / department office",
                    "Contact the concerned welfare office or notified online portal.",
                    "Official Women Welfare department information"
            ));

            schemeRepository.add(new WelfareScheme(
                    "S107", "Senior Citizen Assistance",
                    "Welfare support for senior citizens.",
                    "Financial assistance up to Rs. 25,000.",
                    "Any", "Any", "Any",
                    60, 100, 200000,
                    false, 0,
                    "Any", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Age Proof", "Income Certificate"),
                    "Online / welfare office",
                    "Submit age and income proof to the concerned welfare authority.",
                    "Official Senior Citizen Welfare information"
            ));

            schemeRepository.getAll().get(schemeRepository.size() - 1).setSchemeCategory("Senior Citizens"); // CATEGORY_S107

            schemeRepository.add(new WelfareScheme(
                    "S108", "Low Income Family Support",
                    "Support for families meeting the specified income condition.",
                    "Financial assistance up to Rs. 20,000.",
                    "Any", "Any", "Any",
                    18, 60, 150000,
                    false, 0,
                    "Any", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Income Certificate", "Address Proof"),
                    "Online / department office",
                    "Apply with valid identity, address and income proof.",
                    "Official welfare department information"
            ));

            schemeRepository.getAll().get(schemeRepository.size() - 1).setSchemeCategory("Financial Assistance"); // CATEGORY_S108

            schemeRepository.add(new WelfareScheme(
                    "S109", "Farmer Family Support",
                    "Support for eligible farmer/farmer-family applicants.",
                    "Financial assistance up to Rs. 30,000.",
                    "Any", "Any", "Any",
                    18, 65, 250000,
                    false, 0,
                    "Any", "Any",
                    false, 0, true,
                    documents("Aadhaar Card", "Farmer / Land Record", "Income Certificate"),
                    "Online agriculture portal / office",
                    "Apply through the notified agriculture welfare channel.",
                    "Official Agriculture Department information"
            ));

            schemeRepository.add(new Scholarship(
                    "S110", "Tamil Nadu Student Aid",
                    "Student assistance for eligible students residing in Tamil Nadu.",
                    "Up to Rs. 35,000 per year.",
                    "Any", "Any", "Tamil Nadu",
                    17, 28, 200000,
                    true, 60,
                    "Undergraduate", "Any",
                    false, 0, false,
                    documents("Aadhaar Card", "Nativity / Address Proof",
                            "Mark Sheet", "Income Certificate", "Bonafide Certificate"),
                    "Online application",
                    "Submit the application through the notified Tamil Nadu student welfare channel.",
                    "Official Tamil Nadu education / welfare portal"
            ));

            setSampleSchemeCategories();
            schemeRepository.add(new WelfareScheme(
                    "S111", "Disability Support Assistance",
                    "Support for eligible persons with qualifying disabilities.",
                    "Financial assistance up to Rs. 40,000.",
                    "Any", "Any", "Any",
                    18, 65, 300000,
                    false, 0,
                    "Any", "Any",
                    true, 40, false,
                    documents("Aadhaar Card", "Disability Certificate", "Income Certificate"),
                    "Online / welfare office",
                    "Submit the disability and income certificates through the notified welfare channel.",
                    "Official Disability Welfare information"
            ));
            schemeRepository.getAll().get(schemeRepository.size() - 1).setSchemeCategory("Disability Support");


        } catch (InvalidSchemeException ex) {
            showError("Could not load sample schemes: " + ex.getMessage());
        }
    }

    private List<Document> documents(String... names) {
        ArrayList<Document> list = new ArrayList<>();
        for (String name : names) {
            list.add(new Document(name, true, false, false));
        }
        return list;
    }

    private static final Color PEACOCK_DARK = new Color(0x005F73);
    private static final Color PEACOCK = new Color(0x007F86);
    private static final Color TURQUOISE = new Color(0x00A6A6);
    private static final Color LIGHT_AQUA = new Color(0xDFF7F5);
    private static final Color PAGE_BG = new Color(0xF4FBFA);
    private static final Color WHITE = Color.WHITE;
    private static final Color DARK_TEXT = new Color(0x17324D);
    private static final Color SECONDARY_TEXT = new Color(0x526777);
    private static final Color GOLD = new Color(0xE7B84B);
    private static final Color SUCCESS = new Color(0x2E9B70);
    private static final Color WARNING = new Color(0xD89B2B);
    private static final Color ERROR = new Color(0xC94C4C);
    private static final Color BORDER = new Color(0xD6E5E5);

    private Font font(int style, int size) {
        return new Font("Segoe UI", style, size);
    }

    private JLabel createHeading(String text) {
        JLabel heading = new JLabel(text, SwingConstants.CENTER);
        heading.setFont(font(Font.BOLD, 28));
        heading.setForeground(DARK_TEXT);
        return heading;
    }

    private JLabel createSubHeading(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font(Font.PLAIN, 14));
        label.setForeground(SECONDARY_TEXT);
        return label;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = createButton(text);
        button.setBackground(PEACOCK_DARK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(11, 20, 11, 20));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = createButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(PEACOCK_DARK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PEACOCK, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        return button;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font(Font.BOLD, 13));
        button.setForeground(PEACOCK_DARK);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button.getForeground().equals(Color.WHITE))
                    button.setBackground(TURQUOISE);
                else
                    button.setBackground(LIGHT_AQUA);
            }
            public void mouseExited(MouseEvent e) {
                if (button.getForeground().equals(Color.WHITE))
                    button.setBackground(PEACOCK_DARK);
                else
                    button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    private JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font(Font.BOLD, 12));
        button.setForeground(PEACOCK_DARK);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBBD5D6), 1),
                BorderFactory.createEmptyBorder(7, 12, 7, 12)));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(LIGHT_AQUA);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    private JTextField createStyledTextField(String value) {
        JTextField field = new JTextField(value == null ? "" : value);
        field.setFont(font(Font.PLAIN, 13));
        field.setForeground(DARK_TEXT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xC9DDDE), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return field;
    }

    private <T> JComboBox<T> createStyledComboBox(T[] values) {
        JComboBox<T> box = new JComboBox<>(values);
        box.setFont(font(Font.PLAIN, 13));
        box.setForeground(DARK_TEXT);
        box.setBackground(Color.WHITE);
        return box;
    }

    private JPanel createCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        return card;
    }

    private JPanel createSectionHeader(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel h = new JLabel(title);
        h.setFont(font(Font.BOLD, 19));
        h.setForeground(PEACOCK_DARK);
        JLabel s = new JLabel(subtitle == null ? "" : subtitle);
        s.setFont(font(Font.PLAIN, 12));
        s.setForeground(SECONDARY_TEXT);
        panel.add(h);
        if (subtitle != null && !subtitle.isEmpty()) {
            panel.add(Box.createVerticalStrut(4));
            panel.add(s);
        }
        return panel;
    }

    private JLabel createStatusBadge(String text, Color color) {
        JLabel badge = new JLabel("  " + text.toUpperCase() + "  ");
        badge.setFont(font(Font.BOLD, 11));
        badge.setForeground(color);
        badge.setOpaque(true);
        badge.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 28));
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(color.getRed(), color.getGreen(), color.getBlue(), 90)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        return badge;
    }

    private JProgressBar createProgressBar(int value, Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(Math.max(0, Math.min(100, value)));
        bar.setStringPainted(true);
        bar.setString(value + "%");
        bar.setFont(font(Font.BOLD, 11));
        bar.setForeground(color);
        bar.setBackground(new Color(230, 239, 239));
        bar.setBorderPainted(false);
        return bar;
    }

    private JPanel createVerticalPanel(Color background) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(background);
        return panel;
    }

    private JPanel createMenuCard(String icon, String title, String description,
                                  Color accent, ActionListener action) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(14, 0));
        card.setPreferredSize(new Dimension(390, 105));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel mark = new JPanel(new GridBagLayout());
        mark.setPreferredSize(new Dimension(48, 48));
        mark.setBackground(accent);
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(font(Font.BOLD, 15));
        iconLabel.setForeground(Color.WHITE);
        mark.add(iconLabel);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font(Font.BOLD, 15));
        titleLabel.setForeground(DARK_TEXT);

        JLabel descriptionLabel = new JLabel("<html><div style='width:290px'>" +
                escapeHtml(description) + "</div></html>");
        descriptionLabel.setFont(font(Font.PLAIN, 12));
        descriptionLabel.setForeground(SECONDARY_TEXT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(descriptionLabel);

        card.add(mark, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        MouseAdapter listener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, title));
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 253, 253));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accent, 1),
                        BorderFactory.createEmptyBorder(16, 18, 16, 18)));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        BorderFactory.createEmptyBorder(16, 18, 16, 18)));
            }
        };
        card.addMouseListener(listener);
        mark.addMouseListener(listener);
        iconLabel.addMouseListener(listener);
        textPanel.addMouseListener(listener);
        titleLabel.addMouseListener(listener);
        descriptionLabel.addMouseListener(listener);
        return card;
    }

    private JPanel createStatCard(String number, String label, Color accent) {
        JPanel card = createCard();
        card.setPreferredSize(new Dimension(150, 92));
        JPanel stripe = new JPanel();
        stripe.setBackground(accent);
        stripe.setPreferredSize(new Dimension(5, 1));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(font(Font.BOLD, 25));
        numberLabel.setForeground(accent);
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(font(Font.BOLD, 11));
        textLabel.setForeground(SECONDARY_TEXT);
        text.add(numberLabel);
        text.add(Box.createVerticalStrut(4));
        text.add(textLabel);
        card.add(stripe, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private JPanel createInfoCard(String title, String value) {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel h = new JLabel(title);
        h.setFont(font(Font.BOLD, 13));
        h.setForeground(PEACOCK_DARK);
        JTextArea v = new JTextArea(value == null ? "" : value);
        v.setEditable(false);
        v.setLineWrap(true);
        v.setWrapStyleWord(true);
        v.setFont(font(Font.PLAIN, 13));
        v.setForeground(SECONDARY_TEXT);
        v.setBackground(Color.WHITE);
        v.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        card.add(h);
        card.add(v);
        return card;
    }

    private void styleFormComponent(Component component) {
        if (component instanceof JTextField) {
            JTextField field = (JTextField) component;
            field.setFont(font(Font.PLAIN, 13));
            field.setForeground(DARK_TEXT);
            field.setBackground(Color.WHITE);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0xC9DDDE)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        } else if (component instanceof JComboBox) {
            JComboBox<?> box = (JComboBox<?>) component;
            box.setFont(font(Font.PLAIN, 13));
            box.setForeground(DARK_TEXT);
            box.setBackground(Color.WHITE);
        } else if (component instanceof JTextArea) {
            JTextArea area = (JTextArea) component;
            area.setFont(font(Font.PLAIN, 13));
            area.setForeground(DARK_TEXT);
            area.setBackground(Color.WHITE);
            area.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0xC9DDDE)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        } else if (component instanceof JScrollPane) {
            ((JScrollPane) component).setBorder(BorderFactory.createLineBorder(new Color(0xC9DDDE)));
            if (((JScrollPane) component).getViewport().getView() != null) {
                styleFormComponent(((JScrollPane) component).getViewport().getView());
            }
        }
    }

    private String shortText(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PAGE_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(42, 75, 42, 75));

        JPanel hero = new JPanel(new BorderLayout(30, 0));
        hero.setBackground(PEACOCK_DARK);
        hero.setBorder(BorderFactory.createEmptyBorder(48, 55, 48, 55));

        JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("SCHEMEWISE");
        brand.setFont(font(Font.BOLD, 15));
        brand.setForeground(new Color(190, 245, 241));

        JLabel title = new JLabel("<html>Government Scheme Eligibility<br>and Benefit Discovery</html>");
        title.setFont(font(Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("<html>Discover government schemes that match your profile,<br>eligibility and available documents.</html>");
        subtitle.setFont(font(Font.PLAIN, 14));
        subtitle.setForeground(new Color(225, 244, 243));

        copy.add(brand);
        copy.add(Box.createVerticalStrut(12));
        copy.add(title);
        copy.add(Box.createVerticalStrut(10));
        copy.add(subtitle);
        copy.add(Box.createVerticalStrut(20));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        JButton start = createPrimaryButton("GET STARTED");
        JButton admin = createSecondaryButton("ADMIN PORTAL");
        JButton exit = createSecondaryButton("EXIT");
        admin.setForeground(PEACOCK_DARK);
        exit.setForeground(PEACOCK_DARK);
        start.addActionListener(e -> cardLayout.show(mainPanel, "USER"));
        admin.addActionListener(e -> adminLogin());
        exit.addActionListener(e -> System.exit(0));
        actions.add(start);
        actions.add(admin);
        actions.add(exit);
        copy.add(actions);

        // Clean landing page: only real actions are shown here.
        hero.add(copy, BorderLayout.CENTER);

        panel.add(hero, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(18, 18));
        panel.setBackground(PAGE_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 34, 22, 34));

        JPanel top = new JPanel(new BorderLayout(20, 0));
        top.setOpaque(false);
        JPanel welcome = new JPanel();
        welcome.setOpaque(false);
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("WELCOME, " +
                (currentUser == null ? "USER" : currentUser.getName().toUpperCase()));
        title.setFont(font(Font.BOLD, 27));
        title.setForeground(DARK_TEXT);
        JLabel sub = new JLabel("Find government schemes that match your profile and available documents.");
        sub.setFont(font(Font.PLAIN, 14));
        sub.setForeground(SECONDARY_TEXT);
        welcome.add(title);
        welcome.add(Box.createVerticalStrut(6));
        welcome.add(sub);
        top.add(welcome, BorderLayout.CENTER);

        JPanel profileBox = createCard();
        profileBox.setPreferredSize(new Dimension(220, 90));
        profileBox.setLayout(new BoxLayout(profileBox, BoxLayout.Y_AXIS));
        JLabel p = new JLabel("PROFILE COMPLETION");
        p.setFont(font(Font.BOLD, 11));
        p.setForeground(SECONDARY_TEXT);
        JLabel pct = new JLabel(profileCompletion() + "%");
        pct.setFont(font(Font.BOLD, 23));
        pct.setForeground(PEACOCK_DARK);
        profileBox.add(p);
        profileBox.add(Box.createVerticalStrut(5));
        profileBox.add(createProgressBar(profileCompletion(), TURQUOISE));
        top.add(profileBox, BorderLayout.EAST);

        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 0));
        stats.setOpaque(false);
        stats.add(createStatCard(profileCompletion() + "%", "PROFILE", PEACOCK));
        stats.add(createStatCard(String.valueOf(currentUser == null ? 0 : currentUser.getAvailableDocuments().size()),
                "MY DOCUMENTS", TURQUOISE));
        stats.add(createStatCard(String.valueOf(currentUser == null ? 0 : countEligibleMatches()),
                "MY MATCHES", SUCCESS));
        stats.add(createStatCard(String.valueOf(schemeRepository.size()), "AVAILABLE SCHEMES", PEACOCK_DARK));

        JPanel mainCards = new JPanel(new GridLayout(2, 2, 14, 14));
        mainCards.setOpaque(false);
        mainCards.add(createMenuCard("01", "MY PROFILE",
                "Manage your personal information and eligibility details.", PEACOCK_DARK,
                e -> enterUserProfile()));
        mainCards.add(createMenuCard("02", "MY DOCUMENTS",
                "Manage the documents you currently have.", TURQUOISE,
                e -> documentChecker()));
        mainCards.add(createMenuCard("03", "FIND MY SCHEMES",
                "Check your profile and documents against all available schemes.", GOLD,
                e -> findEligibleSchemes()));
        mainCards.add(createMenuCard("04", "EXPLORE SCHEMES",
                "Browse available government schemes by category or keyword.", PEACOCK,
                e -> displayAllSchemes()));

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(stats, BorderLayout.NORTH);
        center.add(mainCards, BorderLayout.CENTER);

        JButton back = createSecondaryButton("BACK TO HOME");
        back.setMaximumSize(new Dimension(190, 42));
        back.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(back, BorderLayout.SOUTH);
        return panel;
    }

    private int countEligibleMatches() {
        if (currentUser == null) return 0;
        int count = 0;
        for (Scheme scheme : schemeRepository.getAll()) {
            if (scheme.checkEligibility(currentUser).getStatus() == EligibilityStatus.ELIGIBLE) count++;
        }
        return count;
    }

    private int profileCompletion() {
        if (currentUser == null) return 0;
        int total = 10;
        int complete = 0;
        if (!isBlank(currentUser.getName())) complete++;
        if (!isBlank(currentUser.getGender())) complete++;
        if (!isBlank(currentUser.getCategory())) complete++;
        if (!isBlank(currentUser.getState())) complete++;
        if (currentUser.getAge() > 0) complete++;
        if (currentUser.getIncome() >= 0) complete++;
        if (!isBlank(currentUser.getEducationLevel()) && !currentUser.getEducationLevel().equalsIgnoreCase("Not Specified")) complete++;
        if (!isBlank(currentUser.getCourse())) complete++;
        if (currentUser.getMarks() > 0) complete++;
        if (!isBlank(currentUser.getOccupation()) && !currentUser.getOccupation().equalsIgnoreCase("Not Specified")) complete++;
        return Math.min(100, (complete * 100) / total);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(18, 18));
        panel.setBackground(PAGE_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 34, 22, 34));

        JPanel header = new JPanel(new BorderLayout(18, 0));
        header.setBackground(PEACOCK_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("ADMIN PORTAL");
        title.setFont(font(Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("Manage government schemes and maintain scheme information.");
        sub.setFont(font(Font.PLAIN, 13));
        sub.setForeground(new Color(220, 242, 241));
        text.add(title);
        text.add(Box.createVerticalStrut(5));
        text.add(sub);
        header.add(text, BorderLayout.CENTER);

        JPanel stats = new JPanel(new GridLayout(1, 3, 10, 0));
        stats.setOpaque(false);
        int scholarships = 0, welfare = 0;
        for (Scheme s : schemeRepository.getAll()) {
            if ("Scholarship".equalsIgnoreCase(s.getType())) scholarships++;
            else welfare++;
        }
        stats.add(createStatCard(String.valueOf(schemeRepository.size()), "TOTAL SCHEMES", TURQUOISE));
        stats.add(createStatCard(String.valueOf(scholarships), "SCHOLARSHIPS", GOLD));
        stats.add(createStatCard(String.valueOf(welfare), "WELFARE SCHEMES", SUCCESS));
        header.add(stats, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(2, 3, 14, 14));
        cards.setOpaque(false);
        cards.add(createMenuCard("01", "ADD NEW SCHEME", "Create a new government scheme.", SUCCESS, e -> addScheme()));
        cards.add(createMenuCard("02", "VIEW ALL SCHEMES", "Browse and manage available schemes.", PEACOCK, e -> displayAdminSchemes()));
        cards.add(createMenuCard("03", "SEARCH SCHEME", "Quickly find a scheme by name or category.", TURQUOISE, e -> adminSearchScheme()));
        cards.add(createMenuCard("04", "UPDATE SCHEME", "Edit complete scheme information.", GOLD, e -> updateScheme()));
        cards.add(createMenuCard("05", "DELETE SCHEME", "Remove a scheme after confirmation.", ERROR, e -> deleteScheme()));
        cards.add(createMenuCard("06", "EDIT DOCUMENTS", "Manage required scheme documents.", PEACOCK_DARK, e -> editSchemeDocuments()));

        JButton logout = createSecondaryButton("LOG OUT");
        logout.setMaximumSize(new Dimension(170, 42));
        logout.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(header, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);
        panel.add(logout, BorderLayout.SOUTH);
        return panel;
    }

    private void adminLogin() {
        JPasswordField passwordField = new JPasswordField(18);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 18, 8, 18));
        JLabel label = new JLabel("Enter administrator password:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);
        panel.add(passwordField, BorderLayout.CENTER);

        int result = showCenteredConfirm(panel, " Admin Login");

        if (result == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (password.equals(ADMIN_PASSWORD)) {
                showMessageCentered("Login Successful", "Welcome to the Admin Control Center.",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "ADMIN");
            } else {
                showError("Incorrect Password!");
            }
        }
    }

    private void enterUserProfile() {
        try {
            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            JTextField nameField = new JTextField(currentUser == null ? "" : currentUser.getName());
            JComboBox<String> genderBox = new JComboBox<>(
                    new String[]{"Male", "Female", "Other"});
            JComboBox<String> categoryBox = new JComboBox<>(
                    new String[]{"General", "OBC", "SC", "ST"});
            JTextField stateField = new JTextField(currentUser == null ? "" : currentUser.getState());
            JTextField ageField = new JTextField(currentUser == null ? "" : String.valueOf(currentUser.getAge()));
            JTextField incomeField = new JTextField(currentUser == null ? "" : String.valueOf(currentUser.getIncome()));
            JCheckBox studentBox = new JCheckBox("Currently a student",
                    currentUser != null && currentUser.isStudent());
            JComboBox<String> educationBox = new JComboBox<>(
                    new String[]{"Not Specified", "School", "Higher Secondary",
                            "Diploma", "Undergraduate", "Postgraduate"});
            JTextField courseField = new JTextField(currentUser == null ? "" : currentUser.getCourse());
            JTextField yearField = new JTextField(currentUser == null ? "0" : String.valueOf(currentUser.getYearOfStudy()));
            JTextField marksField = new JTextField(currentUser == null ? "0" : String.valueOf(currentUser.getMarks()));
            JTextField occupationField = new JTextField(
                    currentUser == null ? "" : currentUser.getOccupation());
            JCheckBox disabledBox = new JCheckBox("Has disability",
                    currentUser != null && currentUser.isDisabled());
            JTextField disabilityField = new JTextField(
                    currentUser == null ? "0" : String.valueOf(currentUser.getDisabilityPercentage()));
            JCheckBox farmerBox = new JCheckBox("Farmer / farmer family",
                    currentUser != null && currentUser.isFarmer());

            setSelectedIfPresent(genderBox, currentUser == null ? null : currentUser.getGender());
            setSelectedIfPresent(categoryBox, currentUser == null ? null : currentUser.getCategory());
            setSelectedIfPresent(educationBox, currentUser == null ? null : currentUser.getEducationLevel());

            form.add(new JLabel("Name:"));
            form.add(nameField);
            form.add(new JLabel("Gender:"));
            form.add(genderBox);
            form.add(new JLabel("Category:"));
            form.add(categoryBox);
            form.add(new JLabel("State:"));
            form.add(stateField);
            form.add(new JLabel("Age (0-120):"));
            form.add(ageField);
            form.add(new JLabel("Annual Family Income (Rs.):"));
            form.add(incomeField);
            form.add(new JLabel("Student Status:"));
            form.add(studentBox);
            form.add(new JLabel("Education Level:"));
            form.add(educationBox);
            form.add(new JLabel("Course / Stream:"));
            form.add(courseField);
            form.add(new JLabel("Year of Study (0 if N/A):"));
            form.add(yearField);
            form.add(new JLabel("Marks / Percentage (0-100):"));
            form.add(marksField);
            form.add(new JLabel("Occupation:"));
            form.add(occupationField);
            form.add(new JLabel("Disability Status:"));
            form.add(disabledBox);
            form.add(new JLabel("Disability Percentage:"));
            form.add(disabilityField);
            form.add(new JLabel("Farmer Status:"));
            form.add(farmerBox);

            int result = showCenteredConfirm(form, " User Profile");

            if (result != JOptionPane.OK_OPTION) return;

            String name = nameField.getText().trim();
            String state = stateField.getText().trim();

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name should not be empty.");
            }
            if (state.isEmpty()) {
                throw new IllegalArgumentException("State should not be empty.");
            }

            int age = Integer.parseInt(ageField.getText().trim());
            double income = Double.parseDouble(incomeField.getText().trim());
            int year = Integer.parseInt(yearField.getText().trim());
            double marks = Double.parseDouble(marksField.getText().trim());
            double disability = Double.parseDouble(disabilityField.getText().trim());

            String education = (String) educationBox.getSelectedItem();
            String course = courseField.getText().trim();
            String occupation = occupationField.getText().trim();

            if (year < 0 || year > 10) {
                throw new IllegalArgumentException("Year of study must be between 0 and 10.");
            }

            Set<String> existingDocuments = currentUser == null
                    ? new LinkedHashSet<String>() : currentUser.getAvailableDocuments();

            User newUser = new User(
                    name,
                    (String) genderBox.getSelectedItem(),
                    (String) categoryBox.getSelectedItem(),
                    state,
                    age,
                    income,
                    studentBox.isSelected(),
                    education,
                    course,
                    year,
                    marks,
                    occupation.isEmpty() ? "Not Specified" : occupation,
                    disabledBox.isSelected(),
                    disability,
                    farmerBox.isSelected());

            newUser.setAvailableDocuments(existingDocuments);
            newUser.validate();
            currentUser = newUser;

            if (userRepository.size() == 0) {
                userRepository.add(currentUser);
            }

            refreshUserDashboard();
            showMessageCentered("Profile Saved", "Your profile has been saved successfully.", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric details for age, income, year, marks and disability percentage.");
        } catch (InvalidAgeException | InvalidIncomeException |
                 InvalidMarksException ex) {
            showError(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void refreshUserDashboard() {
        Component old = null;
        for (Component component : mainPanel.getComponents()) {
            if ("USER".equals(component.getName())) {
                old = component;
                break;
            }
        }
        if (old != null) mainPanel.remove(old);
        JPanel fresh = createUserPanel();
        fresh.setName("USER");
        mainPanel.add(fresh, "USER");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "USER");
    }

    private void setSelectedIfPresent(JComboBox<String> box, String value) {
        if (value == null) return;
        for (int i = 0; i < box.getItemCount(); i++) {
            if (box.getItemAt(i).equalsIgnoreCase(value)) {
                box.setSelectedIndex(i);
                return;
            }
        }
    }

    private void searchScheme() {
        browseSchemes(true);
    }

    private void adminSearchScheme() {
        String keyword = showInputCentered(
                "Search by scheme name, keyword or internal Scheme ID:",
                " Admin Scheme Search", "");

        if (keyword == null || keyword.trim().isEmpty()) return;

        String key = keyword.trim().toLowerCase();
        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (Scheme scheme : schemeRepository.getAll()) {
            if (scheme.getId().equalsIgnoreCase(keyword.trim())
                    || scheme.getName().toLowerCase().contains(key)
                    || scheme.getSchemeCategory().toLowerCase().contains(key)) {
                result.append(scheme.getDetails())
                        .append("\n\n------------------------\n\n");
                found = true;
            }
        }

        if (found) {
            showText("Admin Search Results", result.toString());
        } else {
            showError("No matching scheme found.");
        }
    }

    private void browseSchemes(boolean allowEligibility) {
        JDialog dialog = new JDialog(this, allowEligibility ? "Find My Scheme" : "Explore Schemes", true);
        dialog.setSize(1000, 720);
        dialog.setMinimumSize(new Dimension(900, 620));
        dialog.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(PAGE_BG);
        root.setBorder(BorderFactory.createEmptyBorder(22, 26, 20, 26));

        JPanel heading = createSectionHeader(
                allowEligibility ? "FIND GOVERNMENT SCHEMES" : "EXPLORE GOVERNMENT SCHEMES",
                allowEligibility
                        ? "Search by name or category and check your eligibility."
                        : "Browse available schemes without needing to know their internal IDs.");

        JTextField searchField = createStyledTextField("");
        searchField.putClientProperty("JTextField.placeholderText", "Search schemes...");
        JComboBox<String> categoryBox = createStyledComboBox(new String[]{
                "All Categories", "Education", "Women & Girls", "Senior Citizens",
                "Financial Assistance", "Farmers & Agriculture", "Disability Support", "Welfare"
        });

        JPanel filterCard = createCard();
        filterCard.setLayout(new BorderLayout(10, 0));
        filterCard.add(searchField, BorderLayout.CENTER);
        filterCard.add(categoryBox, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);
        top.add(heading, BorderLayout.NORTH);
        top.add(filterCard, BorderLayout.CENTER);

        JPanel cards = new JPanel();
        cards.setLayout(new BoxLayout(cards, BoxLayout.Y_AXIS));
        cards.setOpaque(false);

        JScrollPane scroll = new JScrollPane(cards);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.getViewport().setBackground(PAGE_BG);

        JLabel countLabel = new JLabel();
        countLabel.setFont(font(Font.BOLD, 12));
        countLabel.setForeground(SECONDARY_TEXT);

        Runnable refresh = () -> {
            cards.removeAll();
            String key = searchField.getText().trim().toLowerCase();
            String selectedCategory = (String) categoryBox.getSelectedItem();
            int count = 0;

            for (Scheme scheme : schemeRepository.getAll()) {
                boolean categoryMatch = "All Categories".equals(selectedCategory)
                        || scheme.getSchemeCategory().equalsIgnoreCase(selectedCategory);
                boolean textMatch = key.isEmpty()
                        || scheme.getName().toLowerCase().contains(key)
                        || scheme.getDescription().toLowerCase().contains(key)
                        || scheme.getBenefit().toLowerCase().contains(key)
                        || scheme.getSchemeCategory().toLowerCase().contains(key);

                if (categoryMatch && textMatch) {
                    JPanel schemeCard = createSchemeCard(scheme, new Scheme[1], dialog, allowEligibility);
                    schemeCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                    cards.add(schemeCard);
                    cards.add(Box.createVerticalStrut(12));
                    count++;
                }
            }

            if (count == 0) {
                JPanel empty = createCard();
                empty.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                JLabel message = new JLabel("No schemes matched your search.", SwingConstants.CENTER);
                message.setFont(font(Font.PLAIN, 14));
                message.setForeground(SECONDARY_TEXT);
                empty.add(message, BorderLayout.CENTER);
                cards.add(empty);
            }

            countLabel.setText(count + (count == 1 ? " scheme found" : " schemes found"));
            cards.revalidate();
            cards.repaint();
        };

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { refresh.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { refresh.run(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refresh.run(); }
        });
        categoryBox.addActionListener(e -> refresh.run());

        JButton close = createSecondaryButton("CLOSE");
        close.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(countLabel, BorderLayout.WEST);
        bottom.add(close, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        refresh.run();
        dialog.setVisible(true);
    }

    private JPanel createSchemeCard(Scheme scheme, Scheme[] selectedScheme,
                                    JDialog browser, boolean allowEligibility) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(14, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(6, 1));
        accent.setBackground(scheme.getSchemeType().equalsIgnoreCase("Scholarship") ? TURQUOISE : PEACOCK_DARK);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(scheme.getName().toUpperCase());
        name.setFont(font(Font.BOLD | Font.ITALIC, 19));
        name.setForeground(DARK_TEXT);

        JLabel meta = new JLabel(scheme.getSchemeCategory().toUpperCase()
                + "   |   " + scheme.getSchemeType().toUpperCase());
        meta.setFont(font(Font.BOLD, 11));
        meta.setForeground(PEACOCK);

        JLabel description = new JLabel("<html><div style='width:650px'>"
                + escapeHtml(shortText(scheme.getDescription(), 150)) + "</div></html>");
        description.setFont(font(Font.PLAIN, 12));
        description.setForeground(SECONDARY_TEXT);

        JLabel benefit = new JLabel("Benefit: " + escapeHtml(scheme.getBenefit()));
        benefit.setFont(font(Font.BOLD, 12));
        benefit.setForeground(SUCCESS);

        content.add(name);
        content.add(Box.createVerticalStrut(5));
        content.add(meta);
        content.add(Box.createVerticalStrut(9));
        content.add(description);
        content.add(Box.createVerticalStrut(9));
        content.add(benefit);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        actions.setOpaque(false);
        JButton details = createSecondaryButton("VIEW DETAILS");
        JButton eligibility = createPrimaryButton("CHECK ELIGIBILITY");
        details.addActionListener(e -> showSchemeDetails(scheme));
        eligibility.addActionListener(e -> {
            try {
                requireProfile();
                showEligibilityResult(scheme.checkEligibility(currentUser));
            } catch (MissingProfileException ex) {
                showError(ex.getMessage());
            }
        });
        actions.add(details);
        if (allowEligibility) actions.add(eligibility);

        JPanel center = new JPanel(new BorderLayout(14, 0));
        center.setOpaque(false);
        center.add(accent, BorderLayout.WEST);
        center.add(content, BorderLayout.CENTER);

        card.add(center, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }


    private void checkOneScheme() {
        try {
            requireProfile();
            browseSchemes(true);
        } catch (MissingProfileException ex) {
            showError(ex.getMessage());
        }
    }

    private void showEligibilityResult(EligibilityResult result) {
        JDialog dialog = new JDialog(this, "Eligibility Assessment", true);
        dialog.setSize(860, 700);
        dialog.setLocationRelativeTo(this);

        Color statusColor = result.getStatus() == EligibilityStatus.ELIGIBLE ? SUCCESS
                : result.getStatus() == EligibilityStatus.ALMOST_ELIGIBLE ? WARNING : ERROR;

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(PAGE_BG);
        root.setBorder(BorderFactory.createEmptyBorder(22, 25, 20, 25));

        JPanel header = createCard();
        header.setLayout(new BorderLayout(16, 0));

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(result.getScheme().getName().toUpperCase());
        title.setFont(font(Font.BOLD | Font.ITALIC, 23));
        title.setForeground(DARK_TEXT);
        JLabel meta = new JLabel(result.getScheme().getSchemeCategory().toUpperCase()
                + "   |   " + result.getScheme().getSchemeType().toUpperCase());
        meta.setFont(font(Font.BOLD, 11));
        meta.setForeground(PEACOCK);
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(5));
        titleBlock.add(meta);

        JPanel match = new JPanel();
        match.setOpaque(false);
        match.setLayout(new BoxLayout(match, BoxLayout.Y_AXIS));
        JLabel matchLabel = new JLabel("MATCH");
        matchLabel.setFont(font(Font.BOLD, 11));
        matchLabel.setForeground(SECONDARY_TEXT);
        JLabel pct = new JLabel(matchPercent(result) + "%");
        pct.setFont(font(Font.BOLD, 25));
        pct.setForeground(statusColor);
        match.add(matchLabel);
        match.add(pct);
        match.add(createProgressBar(matchPercent(result), statusColor));

        header.add(titleBlock, BorderLayout.CENTER);
        header.add(match, BorderLayout.EAST);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PAGE_BG);

        JPanel statusRow = createCard();
        statusRow.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        statusRow.add(createStatusBadge(result.getStatus().toString().replace('_', ' '), statusColor));
        statusRow.add(new JLabel(result.getStatus() == EligibilityStatus.ELIGIBLE
                ? "All eligibility requirements are satisfied."
                : result.getStatus() == EligibilityStatus.ALMOST_ELIGIBLE
                ? "Some requirements or documents still need attention."
                : "One or more important requirements are not satisfied."));
        content.add(statusRow);
        content.add(Box.createVerticalStrut(12));

        JPanel conditions = createCard();
        conditions.setLayout(new BoxLayout(conditions, BoxLayout.Y_AXIS));
        addResultSection(conditions, "ELIGIBILITY REQUIREMENTS SATISFIED",
                result.getSatisfiedRequirements(), SUCCESS, "✓");
        if (!result.getMissingRequirements().isEmpty()) {
            addResultSection(conditions, "ELIGIBILITY REQUIREMENTS NOT SATISFIED",
                    result.getMissingRequirements(), ERROR, "×");
        }
        content.add(conditions);
        content.add(Box.createVerticalStrut(12));

        JPanel documents = createCard();
        documents.setLayout(new BoxLayout(documents, BoxLayout.Y_AXIS));
        ArrayList<String> have = new ArrayList<>();
        ArrayList<String> missing = new ArrayList<>();
        Set<String> available = currentUser == null ? new LinkedHashSet<String>() : currentUser.getAvailableDocuments();
        for (Document d : result.getScheme().getRequiredDocuments()) {
            if (!d.isRequired()) continue;
            if (available.contains(d.getDocumentName())) have.add(d.getDocumentName());
            else missing.add(d.getDocumentName());
        }
        if (!have.isEmpty()) addResultSection(documents, "DOCUMENTS YOU HAVE", have, SUCCESS, "✓");
        if (!missing.isEmpty()) addResultSection(documents, "DOCUMENTS STILL REQUIRED", missing, WARNING, "×");
        if (have.isEmpty() && missing.isEmpty())
            addResultSection(documents, "DOCUMENTS", Collections.singletonList("No required documents are configured for this scheme."),
                    SECONDARY_TEXT, "-");
        content.add(documents);
        content.add(Box.createVerticalStrut(12));

        String actionText = result.getReport();
        int actionIndex = actionText.indexOf("Action:");
        if (actionIndex >= 0) actionText = actionText.substring(actionIndex + 7).trim();

        JPanel next = createInfoCard("NEXT STEPS", actionText);
        content.add(next);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton close = createSecondaryButton("CLOSE");
        close.addActionListener(e -> dialog.dispose());

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(close, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void addResultSection(JPanel parent, String headingText, List<String> items,
                                  Color color, String mark) {
        JLabel heading = new JLabel(headingText);
        heading.setFont(font(Font.BOLD, 13));
        heading.setForeground(color);
        parent.add(heading);
        parent.add(Box.createVerticalStrut(7));
        if (items.isEmpty()) {
            JLabel empty = new JLabel("None");
            empty.setFont(font(Font.PLAIN, 12));
            empty.setForeground(SECONDARY_TEXT);
            parent.add(empty);
        } else {
            for (String item : items) {
                JPanel row = new JPanel(new BorderLayout(8, 0));
                row.setOpaque(false);
                row.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                JLabel marker = new JLabel(mark);
                marker.setFont(font(Font.BOLD, 13));
                marker.setForeground(color);
                JLabel label = new JLabel("<html>" + escapeHtml(item) + "</html>");
                label.setFont(font(Font.PLAIN, 12));
                label.setForeground(DARK_TEXT);
                row.add(marker, BorderLayout.WEST);
                row.add(label, BorderLayout.CENTER);
                parent.add(row);
            }
        }
        parent.add(Box.createVerticalStrut(10));
    }

    private void showSchemeDetails(Scheme scheme) {
        JDialog dialog = new JDialog(this, "Scheme Details", true);
        dialog.setSize(860, 700);
        dialog.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(PAGE_BG);
        root.setBorder(BorderFactory.createEmptyBorder(22, 25, 20, 25));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PEACOCK_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(22, 25, 22, 25));
        JPanel h = new JPanel();
        h.setOpaque(false);
        h.setLayout(new BoxLayout(h, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(scheme.getName().toUpperCase());
        title.setFont(font(Font.BOLD | Font.ITALIC, 25));
        title.setForeground(Color.WHITE);
        JLabel meta = new JLabel(scheme.getSchemeCategory().toUpperCase()
                + "   |   " + scheme.getSchemeType().toUpperCase());
        meta.setFont(font(Font.BOLD, 11));
        meta.setForeground(new Color(205, 241, 239));
        h.add(title);
        h.add(Box.createVerticalStrut(6));
        h.add(meta);
        header.add(h, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setBackground(PAGE_BG);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        body.add(createInfoCard("ABOUT THE SCHEME", scheme.getDescription()));
        body.add(Box.createVerticalStrut(10));
        body.add(createInfoCard("BENEFITS", scheme.getBenefit()));
        body.add(Box.createVerticalStrut(10));

        String eligibility = "Age: " + scheme.getMinAge() + " - " + scheme.getMaxAge()
                + "\nMaximum annual income: Rs. " + String.format("%.0f", scheme.getMaxIncome())
                + "\nGender: " + scheme.getGender()
                + "\nBeneficiary category: " + scheme.getCategory()
                + "\nState: " + scheme.getState()
                + "\nStudent required: " + (scheme.isStudentRequired() ? "Yes" : "No")
                + "\nMinimum marks: " + (scheme.getMinMarks() <= 0 ? "Not specified" : scheme.getMinMarks() + "%")
                + "\nEducation: " + scheme.getEducationRequirement()
                + "\nOccupation: " + scheme.getOccupationRequirement()
                + "\nDisability required: " + (scheme.isDisabilityRequired()
                    ? "Yes (" + scheme.getMinDisabilityPercentage() + "% minimum)" : "No")
                + "\nFarmer required: " + (scheme.isFarmerRequired() ? "Yes" : "No");
        body.add(createInfoCard("ELIGIBILITY REQUIREMENTS", eligibility));
        body.add(Box.createVerticalStrut(10));

        StringBuilder docs = new StringBuilder();
        if (scheme.getRequiredDocuments().isEmpty()) docs.append("No documents specified.");
        else {
            for (Document d : scheme.getRequiredDocuments()) {
                docs.append(d.getDocumentName())
                    .append(d.isRequired() ? "  |  Required" : "  |  Optional")
                    .append("\n");
            }
        }
        body.add(createInfoCard("REQUIRED DOCUMENTS", docs.toString().trim()));
        body.add(Box.createVerticalStrut(10));

        body.add(createInfoCard("APPLICATION PROCESS",
                scheme.getApplicationMethod() + "\n" + scheme.getApplicationInformation()
                        + "\n" + scheme.getApplicationWebsite()));

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton check = createPrimaryButton("CHECK MY ELIGIBILITY");
        JButton close = createSecondaryButton("CLOSE");
        check.addActionListener(e -> {
            try {
                requireProfile();
                showEligibilityResult(scheme.checkEligibility(currentUser));
            } catch (MissingProfileException ex) {
                showError(ex.getMessage());
            }
        });
        close.addActionListener(e -> dialog.dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.add(check);
        buttons.add(close);

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void addDetailBlock(JPanel parent, String headingText, String value) {
        JLabel heading = new JLabel(headingText);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 13));
        heading.setForeground(new Color(55, 90, 145));
        parent.add(heading);
        parent.add(Box.createVerticalStrut(5));
        JTextArea text = new JTextArea(value);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.setForeground(new Color(70, 80, 90));
        text.setBackground(Color.WHITE);
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        parent.add(text);
        parent.add(Box.createVerticalStrut(6));
    }

    private void findEligibleSchemes() {
        try {
            requireProfile();
            showDocumentCollection(true);
        } catch (MissingProfileException ex) {
            int result = showCenteredYesNo("Please complete your profile before finding suitable schemes.\n\nOpen My Profile now?", "Profile Required");
            if (result == JOptionPane.YES_OPTION) enterUserProfile();
        }
    }

    private void showDocumentCollection(boolean continueToMatches) {
        requireProfileUnchecked();

        LinkedHashSet<String> allDocuments = new LinkedHashSet<>();
        for (Scheme scheme : schemeRepository.getAll()) {
            for (Document document : scheme.getRequiredDocuments()) {
                if (document.isRequired()) allDocuments.add(document.getDocumentName());
            }
        }

        JDialog dialog = new JDialog(this, "My Documents", true);
        dialog.setSize(760, 650);
        dialog.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(PAGE_BG);
        root.setBorder(BorderFactory.createEmptyBorder(22, 25, 20, 25));

        root.add(createSectionHeader("MY DOCUMENTS",
                "Select the documents currently available to you. These selections are used across all schemes."),
                BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(PAGE_BG);

        ArrayList<JCheckBox> boxes = new ArrayList<>();
        Set<String> current = currentUser.getAvailableDocuments();

        for (String documentName : allDocuments) {
            JPanel row = createCard();
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
            row.setLayout(new BorderLayout(10, 0));

            JCheckBox box = new JCheckBox(documentName);
            box.setFont(font(Font.PLAIN, 13));
            box.setForeground(DARK_TEXT);
            box.setOpaque(false);
            box.setSelected(current.contains(documentName));

            JLabel state = new JLabel(box.isSelected() ? "AVAILABLE" : "NOT AVAILABLE");
            state.setFont(font(Font.BOLD, 10));
            state.setForeground(box.isSelected() ? SUCCESS : SECONDARY_TEXT);

            box.addActionListener(e -> {
                state.setText(box.isSelected() ? "AVAILABLE" : "NOT AVAILABLE");
                state.setForeground(box.isSelected() ? SUCCESS : SECONDARY_TEXT);
                row.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(box.isSelected() ? TURQUOISE : BORDER, 1),
                        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
            });

            row.add(box, BorderLayout.CENTER);
            row.add(state, BorderLayout.EAST);
            list.add(row);
            list.add(Box.createVerticalStrut(8));
            boxes.add(box);
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton selectAll = createSecondaryButton("SELECT ALL");
        JButton clearAll = createSecondaryButton("CLEAR ALL");
        JButton cancel = createSecondaryButton("CANCEL");
        JButton save = createPrimaryButton(continueToMatches ? "SAVE & FIND MY SCHEMES" : "SAVE DOCUMENTS");

        selectAll.addActionListener(e -> boxes.forEach(b -> b.setSelected(true)));
        clearAll.addActionListener(e -> boxes.forEach(b -> b.setSelected(false)));
        cancel.addActionListener(e -> dialog.dispose());
        save.addActionListener(e -> {
            LinkedHashSet<String> selected = new LinkedHashSet<>();
            for (JCheckBox box : boxes) if (box.isSelected()) selected.add(box.getText());
            currentUser.setAvailableDocuments(selected);
            dialog.dispose();
            refreshUserDashboard();
            if (continueToMatches) showCombinedSchemeResults();
            else showMessageCentered("Documents Saved",
                    "Your document information has been updated and will be used across all schemes.",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        buttons.setOpaque(false);
        buttons.add(selectAll);
        buttons.add(clearAll);
        buttons.add(cancel);
        buttons.add(save);

        root.add(scroll, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void documentChecker() {
        try {
            requireProfile();
            showDocumentCollection(false);
        } catch (MissingProfileException ex) {
            int result = showCenteredYesNo("Please complete your profile before managing your documents.\n\nOpen My Profile now?", "Profile Required");
            if (result == JOptionPane.YES_OPTION) enterUserProfile();
        }
    }

    private void requireProfileUnchecked() {
        if (currentUser == null) throw new IllegalStateException("Please complete your profile first.");
    }

    private void showCombinedSchemeResults() {
        SwingWorker<List<EligibilityResult>, Void> worker = new SwingWorker<List<EligibilityResult>, Void>() {
            @Override
            protected List<EligibilityResult> doInBackground() {
                List<Scheme> schemes = schemeRepository.getAll();
                ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
                List<Future<EligibilityResult>> futures = new ArrayList<>();
                for (Scheme scheme : schemes) {
                    futures.add(executor.submit(new Callable<EligibilityResult>() {
                        @Override public EligibilityResult call() { return scheme.checkEligibility(currentUser); }
                    }));
                }
                ArrayList<EligibilityResult> results = new ArrayList<>();
                try {
                    for (Future<EligibilityResult> future : futures) results.add(future.get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex.getCause());
                } finally {
                    executor.shutdown();
                }
                return results;
            }

            @Override
            protected void done() {
                try {
                    showMatchResults(get());
                } catch (Exception ex) {
                    showError("We couldn't complete the scheme matching process: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void showMatchResults(List<EligibilityResult> results) {
        ArrayList<EligibilityResult> best = new ArrayList<>();
        ArrayList<EligibilityResult> almost = new ArrayList<>();
        ArrayList<EligibilityResult> not = new ArrayList<>();
        for (EligibilityResult result : results) {
            if (result.getStatus() == EligibilityStatus.ELIGIBLE) best.add(result);
            else if (result.getStatus() == EligibilityStatus.ALMOST_ELIGIBLE) almost.add(result);
            else not.add(result);
        }
        Comparator<EligibilityResult> ranker = new Comparator<EligibilityResult>() {
            @Override public int compare(EligibilityResult a, EligibilityResult b) {
                return Integer.compare(matchPercent(b), matchPercent(a));
            }
        };
        Collections.sort(best, ranker);
        Collections.sort(almost, ranker);
        Collections.sort(not, ranker);

        JDialog dialog = new JDialog(this, "Your Scheme Matches", true);
        dialog.setSize(1020, 740);
        dialog.setLocationRelativeTo(this);
        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(new Color(246, 249, 252));
        root.setBorder(BorderFactory.createEmptyBorder(22, 25, 20, 25));

        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);
        JPanel ht = new JPanel();
        ht.setOpaque(false);
        ht.setLayout(new BoxLayout(ht, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("YOUR SCHEME MATCHES");
        title.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
        title.setForeground(new Color(35,55,80));
        JLabel sub = new JLabel("Based on your profile, eligibility and available documents");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(90,105,120));
        ht.add(title); ht.add(Box.createVerticalStrut(4)); ht.add(sub);
        header.add(ht, BorderLayout.CENTER);

        JPanel summary = new JPanel(new GridLayout(1,3,8,0));
        summary.setOpaque(false);
        summary.add(createStatCard(String.valueOf(best.size()), "BEST MATCHES", new Color(45,130,85)));
        summary.add(createStatCard(String.valueOf(almost.size()), "ALMOST ELIGIBLE", new Color(190,130,35)));
        summary.add(createStatCard(String.valueOf(not.size()), "NOT ELIGIBLE", new Color(170,80,85)));
        header.add(summary, BorderLayout.EAST);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        addMatchSection(content, "MY BEST MATCHES", best, new Color(45,130,85));
        addMatchSection(content, "ALMOST ELIGIBLE", almost, new Color(190,130,35));
        addMatchSection(content, "NOT ELIGIBLE", not, new Color(170,80,85));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton close = createButton("Close");
        close.addActionListener(e -> dialog.dispose());
        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(close, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private int matchPercent(EligibilityResult result) {
        int eligibilityTotal = result.getSatisfiedRequirements().size() + result.getMissingRequirements().size();
        int documentTotal = result.getScheme().getRequiredDocuments().size();
        int documentAvailable = documentTotal - result.getMissingDocuments().size();
        int total = eligibilityTotal + documentTotal;
        if (total == 0) return 100;
        return (int)Math.round((eligibilityTotal == 0 ? 0 : (result.getSatisfiedRequirements().size() * 100.0) / eligibilityTotal) * 0.7
                + (documentTotal == 0 ? 100 : (documentAvailable * 100.0) / documentTotal) * 0.3);
    }

    private void addMatchSection(JPanel parent, String headingText, List<EligibilityResult> results, Color color) {
        JLabel heading = new JLabel(headingText + "  (" + results.size() + ")");
        heading.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
        heading.setForeground(color);
        heading.setBorder(BorderFactory.createEmptyBorder(12, 2, 8, 2));
        parent.add(heading);
        if (results.isEmpty()) {
            JLabel empty = new JLabel("No schemes in this group.");
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            empty.setForeground(new Color(110,120,130));
            parent.add(empty);
            return;
        }
        for (EligibilityResult result : results) {
            parent.add(createMatchCard(result, color));
            parent.add(Box.createVerticalStrut(10));
        }
    }

    private JPanel createMatchCard(EligibilityResult result, Color statusColor) {
        JPanel card = createCard();
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(result.getScheme().getName().toUpperCase());
        name.setFont(font(Font.BOLD | Font.ITALIC, 18));
        name.setForeground(DARK_TEXT);

        JLabel meta = new JLabel(result.getScheme().getSchemeCategory().toUpperCase()
                + "   |   " + result.getScheme().getSchemeType().toUpperCase());
        meta.setFont(font(Font.BOLD, 11));
        meta.setForeground(PEACOCK);

        JLabel benefit = new JLabel("<html>Benefit: " + escapeHtml(result.getScheme().getBenefit()) + "</html>");
        benefit.setFont(font(Font.PLAIN, 12));
        benefit.setForeground(SECONDARY_TEXT);

        int percent = matchPercent(result);
        JLabel counts = new JLabel(
                result.getSatisfiedRequirements().size() + " eligibility conditions satisfied  |  "
                + (result.getScheme().getRequiredDocuments().size() - result.getMissingDocuments().size())
                + "/" + result.getScheme().getRequiredDocuments().size() + " documents available");
        counts.setFont(font(Font.PLAIN, 11));
        counts.setForeground(SECONDARY_TEXT);

        left.add(name);
        left.add(Box.createVerticalStrut(5));
        left.add(meta);
        left.add(Box.createVerticalStrut(7));
        left.add(benefit);
        left.add(Box.createVerticalStrut(9));
        left.add(counts);

        JPanel match = new JPanel();
        match.setOpaque(false);
        match.setPreferredSize(new Dimension(190, 75));
        match.setLayout(new BoxLayout(match, BoxLayout.Y_AXIS));
        JLabel matchLabel = new JLabel("MATCH " + percent + "%");
        matchLabel.setFont(font(Font.BOLD, 13));
        matchLabel.setForeground(statusColor);
        match.add(matchLabel);
        match.add(Box.createVerticalStrut(7));
        match.add(createProgressBar(percent, statusColor));
        match.add(Box.createVerticalStrut(7));
        match.add(createStatusBadge(result.getStatus().toString().replace('_', ' '), statusColor));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        actions.setOpaque(false);
        JButton details = createSecondaryButton("VIEW DETAILS");
        JButton next = createPrimaryButton(result.getStatus() == EligibilityStatus.ELIGIBLE
                ? "APPLICATION INFORMATION" : "VIEW WHAT'S MISSING");
        details.addActionListener(e -> showSchemeDetails(result.getScheme()));
        next.addActionListener(e -> showEligibilityResult(result));
        actions.add(details);
        actions.add(next);

        JPanel center = new JPanel(new BorderLayout(15, 8));
        center.setOpaque(false);
        center.add(left, BorderLayout.CENTER);
        center.add(match, BorderLayout.EAST);

        card.add(center, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }

    private void displayAllSchemes() {
        browseSchemes(false);
    }

    private void displayAdminSchemes() {
        JDialog dialog = new JDialog(this, "Manage Government Schemes", true);
        dialog.setSize(1020, 700);
        dialog.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(PAGE_BG);
        root.setBorder(BorderFactory.createEmptyBorder(22, 26, 20, 26));

        root.add(createSectionHeader("ALL GOVERNMENT SCHEMES",
                "View, edit or remove schemes. Scheme ID is shown for administration but never required from users."),
                BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        for (Scheme scheme : schemeRepository.getAll()) {
            JPanel card = createCard();
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

            JPanel text = new JPanel();
            text.setOpaque(false);
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

            JLabel id = new JLabel("SCHEME ID  " + scheme.getId());
            id.setFont(font(Font.BOLD, 10));
            id.setForeground(SECONDARY_TEXT);

            JLabel name = new JLabel(scheme.getName().toUpperCase());
            name.setFont(font(Font.BOLD | Font.ITALIC, 17));
            name.setForeground(DARK_TEXT);

            JLabel meta = new JLabel(scheme.getSchemeCategory().toUpperCase()
                    + "   |   " + scheme.getSchemeType().toUpperCase());
            meta.setFont(font(Font.BOLD, 11));
            meta.setForeground(PEACOCK);

            JLabel benefit = new JLabel("<html>" + escapeHtml(shortText(scheme.getBenefit(), 105)) + "</html>");
            benefit.setFont(font(Font.PLAIN, 12));
            benefit.setForeground(SECONDARY_TEXT);

            text.add(id);
            text.add(Box.createVerticalStrut(4));
            text.add(name);
            text.add(Box.createVerticalStrut(4));
            text.add(meta);
            text.add(Box.createVerticalStrut(5));
            text.add(benefit);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
            actions.setOpaque(false);
            JButton view = createSecondaryButton("VIEW");
            JButton edit = createSecondaryButton("EDIT");
            JButton del = createSmallButton("DELETE");
            del.setForeground(ERROR);

            view.addActionListener(e -> showSchemeDetails(scheme));
            edit.addActionListener(e -> {
                dialog.dispose();
                editSchemeForm(scheme);
            });
            del.addActionListener(e -> {
                int c = showCenteredYesNo(
                        "Are you sure you want to permanently remove:\n\n"
                        + scheme.getName() + "\n\nScheme ID: " + scheme.getId()
                        + "\n\nThis action cannot be undone.",
                        "Delete Scheme");
                if (c == JOptionPane.YES_OPTION) {
                    schemeRepository.remove(scheme);
                    dialog.dispose();
                    refreshAdminDashboard();
                    displayAdminSchemes();
                }
            });

            actions.add(view);
            actions.add(edit);
            actions.add(del);

            card.add(text, BorderLayout.CENTER);
            card.add(actions, BorderLayout.EAST);
            list.add(card);
            list.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton close = createSecondaryButton("CLOSE");
        close.addActionListener(e -> dialog.dispose());

        root.add(scroll, BorderLayout.CENTER);
        root.add(close, BorderLayout.SOUTH);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void addScheme() {
        try {
            Scheme newScheme = collectSchemeFromAdmin(null);
            if (newScheme == null) return;

            for (Scheme scheme : schemeRepository.getAll()) {
                if (scheme.getId().equalsIgnoreCase(newScheme.getId())) {
                    throw new InvalidSchemeException("Scheme ID already exists.");
                }
            }

            schemeRepository.add(newScheme);
            showMessageCentered("Admin", "New scheme added successfully!", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric scheme details.");
        } catch (InvalidSchemeException ex) {
            showError(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }


    private void updateScheme() {
        try {
            Scheme existing = chooseSchemeFromList("EDIT GOVERNMENT SCHEME");
            if (existing == null) return;
            editSchemeForm(existing);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void editSchemeForm(Scheme existing) {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JTextField nameField = new JTextField(existing.getName(), 30);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Scholarship", "Welfare Scheme"});
        setSelectedIfPresent(typeBox, existing.getType());
        JComboBox<String> schemeCategoryBox = new JComboBox<>(new String[]{"Education","Women & Girls","Senior Citizens","Financial Assistance","Farmers & Agriculture","Disability Support","Welfare"});
        setSelectedIfPresent(schemeCategoryBox, existing.getSchemeCategory());
        JTextArea descriptionArea = new JTextArea(existing.getDescription(), 4, 30);
        JTextField benefitField = new JTextField(existing.getBenefit(), 30);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Any","Male","Female","Other"}); setSelectedIfPresent(genderBox, existing.getGender());
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Any","General","OBC","SC","ST"}); setSelectedIfPresent(categoryBox, existing.getCategory());
        JTextField stateField = new JTextField(existing.getState(), 30);
        JTextField minAgeField = new JTextField(String.valueOf(existing.getMinAge()), 10);
        JTextField maxAgeField = new JTextField(String.valueOf(existing.getMaxAge()), 10);
        JTextField incomeField = new JTextField(String.valueOf(existing.getMaxIncome()), 10);
        JCheckBox studentBox = new JCheckBox("Student required", existing.isStudentRequired());
        JTextField marksField = new JTextField(String.valueOf(existing.getMinMarks()), 10);
        JComboBox<String> educationBox = new JComboBox<>(new String[]{"Any","Not Specified","School","Higher Secondary","Diploma","Undergraduate","Postgraduate"}); setSelectedIfPresent(educationBox, existing.getEducationRequirement());
        JTextField occupationField = new JTextField(existing.getOccupationRequirement(), 20);
        JCheckBox disabilityBox = new JCheckBox("Disability required", existing.isDisabilityRequired());
        JTextField disabilityField = new JTextField(String.valueOf(existing.getMinDisabilityPercentage()), 10);
        JCheckBox farmerBox = new JCheckBox("Farmer required", existing.isFarmerRequired());
        JTextArea documentsArea = new JTextArea(6, 30);
        StringBuilder docs = new StringBuilder();
        for (Document d : existing.getRequiredDocuments()) docs.append(d.getDocumentName()).append("\n");
        documentsArea.setText(docs.toString());
        JTextField applicationMethodField = new JTextField(existing.getApplicationMethod(), 30);
        JTextArea applicationInfoArea = new JTextArea(existing.getApplicationInformation(), 4, 30);
        JTextField websiteField = new JTextField(existing.getApplicationWebsite(), 30);

        int row = 0;
        addFormSection(form, gbc, row++, "BASIC INFORMATION");
        row++;
        row=addFormField(form,gbc,row,"Scheme ID",new JLabel(existing.getId()),new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Scheme Name",nameField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Scheme Type",typeBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Scheme Category",schemeCategoryBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Description",new JScrollPane(descriptionArea),new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Benefit",benefitField,new Font("Segoe UI",Font.BOLD,12));
        addFormSection(form,gbc,row++,"ELIGIBILITY"); row++;
        row=addFormField(form,gbc,row,"Gender",genderBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Category",categoryBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"State",stateField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Minimum Age",minAgeField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Maximum Age",maxAgeField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Maximum Annual Income",incomeField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Student Required",studentBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Minimum Marks",marksField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Education",educationBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Occupation",occupationField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Disability Required",disabilityBox,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Minimum Disability %",disabilityField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Farmer Required",farmerBox,new Font("Segoe UI",Font.BOLD,12));
        addFormSection(form,gbc,row++,"REQUIRED DOCUMENTS"); row++;
        row=addFormField(form,gbc,row,"Documents (one per line)",new JScrollPane(documentsArea),new Font("Segoe UI",Font.BOLD,12));
        addFormSection(form,gbc,row++,"APPLICATION"); row++;
        row=addFormField(form,gbc,row,"Application Method",applicationMethodField,new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Application Information",new JScrollPane(applicationInfoArea),new Font("Segoe UI",Font.BOLD,12));
        row=addFormField(form,gbc,row,"Website / Information",websiteField,new Font("Segoe UI",Font.BOLD,12));

        JScrollPane scroll = new JScrollPane(form);
        scroll.setPreferredSize(new Dimension(800,620));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JLabel title = new JLabel("EDIT GOVERNMENT SCHEME");
        title.setFont(new Font("Segoe UI",Font.BOLD|Font.ITALIC,22));
        title.setForeground(new Color(35,55,80));
        JPanel wrapper = new JPanel(new BorderLayout(10,10));
        wrapper.setBackground(new Color(246,249,252));
        wrapper.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        wrapper.add(title,BorderLayout.NORTH); wrapper.add(scroll,BorderLayout.CENTER);

        int result=showCenteredConfirm(wrapper,"Edit Government Scheme");
        if(result!=JOptionPane.OK_OPTION) return;

        try {
            int minAge=Integer.parseInt(minAgeField.getText().trim());
            int maxAge=Integer.parseInt(maxAgeField.getText().trim());
            double maxIncome=Double.parseDouble(incomeField.getText().trim());
            double minMarks=Double.parseDouble(marksField.getText().trim());
            double disability=Double.parseDouble(disabilityField.getText().trim());
            if(nameField.getText().trim().isEmpty()||stateField.getText().trim().isEmpty()||descriptionArea.getText().trim().isEmpty()||benefitField.getText().trim().isEmpty()) throw new InvalidSchemeException("Please complete all required basic fields.");
            if(minAge<0||maxAge<minAge||maxAge>120||maxIncome<0||minMarks<0||minMarks>100||disability<0||disability>100) throw new InvalidSchemeException("Please enter valid eligibility values.");

            existing.setName(nameField.getText().trim()); existing.setDescription(descriptionArea.getText().trim()); existing.setBenefit(benefitField.getText().trim());
            existing.setSchemeCategory((String)schemeCategoryBox.getSelectedItem()); existing.setGender((String)genderBox.getSelectedItem()); existing.setCategory((String)categoryBox.getSelectedItem()); existing.setState(stateField.getText().trim());
            existing.setMinAge(minAge); existing.setMaxAge(maxAge); existing.setMaxIncome(maxIncome); existing.setStudentRequired(studentBox.isSelected()); existing.setMinMarks(minMarks); existing.setEducationRequirement((String)educationBox.getSelectedItem());
            existing.setOccupationRequirement(occupationField.getText().trim().isEmpty()?"Any":occupationField.getText().trim()); existing.setDisabilityRequired(disabilityBox.isSelected()); existing.setMinDisabilityPercentage(disability); existing.setFarmerRequired(farmerBox.isSelected());
            existing.updateApplication(applicationMethodField.getText().trim(),applicationInfoArea.getText().trim(),websiteField.getText().trim());
            ArrayList<Document> newDocs=new ArrayList<>(); LinkedHashSet<String> unique=new LinkedHashSet<>();
            for(String line:documentsArea.getText().split("\\R")){ if(!line.trim().isEmpty()&&unique.add(line.trim())) newDocs.add(new Document(line.trim(),true)); }
            existing.replaceDocuments(newDocs);
            refreshAdminDashboard();
            showMessageCentered("Scheme Updated","The scheme has been updated successfully.",JOptionPane.INFORMATION_MESSAGE);
        } catch(NumberFormatException ex){ showError("Please enter valid numeric values."); } catch(InvalidSchemeException ex){ showError(ex.getMessage()); }
    }

    private void refreshAdminDashboard() {
        Component old=null; for(Component c:mainPanel.getComponents()) if("ADMIN".equals(c.getName())) {old=c;break;}
        if(old!=null) mainPanel.remove(old);
        JPanel fresh=createAdminPanel(); fresh.setName("ADMIN"); mainPanel.add(fresh,"ADMIN"); mainPanel.revalidate(); mainPanel.repaint(); cardLayout.show(mainPanel,"ADMIN");
    }

    private void deleteScheme() {
        try {
            Scheme scheme = chooseSchemeFromList("Select a scheme to delete");
            if (scheme == null) return;

            int confirm = showCenteredYesNo(
                    "Are you sure you want to delete \"" + scheme.getName() + "\"?\n\n"
                            + "This action cannot be undone.",
                    "Confirm Delete");

            if (confirm == JOptionPane.YES_OPTION) {
                schemeRepository.remove(scheme);
                showMessageCentered("Admin", "Scheme deleted successfully.", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void editSchemeDocuments() {
        try {
            Scheme scheme = chooseSchemeFromList("Select a scheme to edit documents");
            if (scheme == null) return;

            String existing = "";
            for (Document d : scheme.getRequiredDocuments()) {
                existing += d.getDocumentName() + "\n";
            }

            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter one required document per line:\n"
                            + "(Leave blank to keep existing documents.)\n\n"
                            + "Current:\n" + existing);

            if (input == null || input.trim().isEmpty()) return;

            String[] names = input.split("\\R");
            ArrayList<Document> docs = new ArrayList<>();

            for (String name : names) {
                if (!name.trim().isEmpty()) {
                    docs.add(new Document(name.trim(), true, false, false));
                }
            }

            scheme.replaceDocuments(docs);

            showMessageCentered("Admin", "Required documents updated successfully.", JOptionPane.INFORMATION_MESSAGE);

        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private Scheme collectSchemeFromAdmin(Scheme ignored) throws InvalidSchemeException {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(249, 251, 255));
        form.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(
                new String[]{"Scholarship", "Welfare Scheme"});
        JComboBox<String> schemeCategoryBox = new JComboBox<>(new String[]{
                "Education", "Women & Girls", "Senior Citizens",
                "Financial Assistance", "Farmers & Agriculture",
                "Disability Support", "Welfare", "General"});
        JTextArea descriptionArea = new JTextArea(3, 20);
        JTextField benefitField = new JTextField();

        JComboBox<String> genderBox = new JComboBox<>(
                new String[]{"Any", "Male", "Female", "Other"});
        JComboBox<String> categoryBox = new JComboBox<>(
                new String[]{"Any", "General", "OBC", "SC", "ST"});
        JTextField stateField = new JTextField("Any");

        JTextField minAgeField = new JTextField("16");
        JTextField maxAgeField = new JTextField("60");
        JTextField maxIncomeField = new JTextField("300000");
        JTextField minMarksField = new JTextField("0");

        JComboBox<String> educationBox = new JComboBox<>(
                new String[]{"Any", "School", "Higher Secondary",
                        "Diploma", "Undergraduate", "Postgraduate"});
        JTextField occupationField = new JTextField("Any");

        JCheckBox studentBox = new JCheckBox("Student only");
        JCheckBox disabilityBox = new JCheckBox("Disability required");
        JTextField disabilityField = new JTextField("0");
        JCheckBox farmerBox = new JCheckBox("Farmer / farmer family");

        JTextArea documentsArea = new JTextArea(4, 20);
        documentsArea.setLineWrap(true);
        documentsArea.setWrapStyleWord(true);

        JTextField applicationMethodField = new JTextField();
        JTextArea applicationInfoArea = new JTextArea(3, 20);
        JTextField websiteField = new JTextField();

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        applicationInfoArea.setLineWrap(true);
        applicationInfoArea.setWrapStyleWord(true);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        int row = 0;
        addFormSection(form, gbc, row++, "1. BASIC SCHEME INFORMATION");
        row = addFormField(form, gbc, row, "Scheme ID", idField, labelFont);
        row = addFormField(form, gbc, row, "Scheme Name", nameField, labelFont);
        row = addFormField(form, gbc, row, "Scheme Type", typeBox, labelFont);
        row = addFormField(form, gbc, row, "Scheme Category", schemeCategoryBox, labelFont);
        row = addFormField(form, gbc, row, "Description", new JScrollPane(descriptionArea), labelFont);
        row = addFormField(form, gbc, row, "Benefit / Assistance", benefitField, labelFont);

        addFormSection(form, gbc, row++, "2. ELIGIBILITY PROFILE");
        row = addFormField(form, gbc, row, "Gender", genderBox, labelFont);
        row = addFormField(form, gbc, row, "Category", categoryBox, labelFont);
        row = addFormField(form, gbc, row, "State", stateField, labelFont);
        row = addFormField(form, gbc, row, "Minimum Age", minAgeField, labelFont);
        row = addFormField(form, gbc, row, "Maximum Age", maxAgeField, labelFont);
        row = addFormField(form, gbc, row, "Maximum Annual Income (Rs.)", maxIncomeField, labelFont);
        row = addFormField(form, gbc, row, "Minimum Marks (%)", minMarksField, labelFont);
        row = addFormField(form, gbc, row, "Education", educationBox, labelFont);
        row = addFormField(form, gbc, row, "Occupation", occupationField, labelFont);

        addFormSection(form, gbc, row++, "3. SPECIAL CONDITIONS");
        row = addFormField(form, gbc, row, "Student Requirement", studentBox, labelFont);
        row = addFormField(form, gbc, row, "Disability Requirement", disabilityBox, labelFont);
        row = addFormField(form, gbc, row, "Minimum Disability (%)", disabilityField, labelFont);
        row = addFormField(form, gbc, row, "Farmer Requirement", farmerBox, labelFont);

        addFormSection(form, gbc, row++, "4. DOCUMENTS & APPLICATION");
        JLabel documentHint = new JLabel(
                "<html>Enter one document per line.<br>Example: Aadhaar Card, Mark Sheet, Income Certificate</html>");
        documentHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        row = addFormField(form, gbc, row, "Required Documents", new JScrollPane(documentsArea), labelFont);
        row = addFormField(form, gbc, row, "Application Method", applicationMethodField, labelFont);
        row = addFormField(form, gbc, row, "Application Information",
                new JScrollPane(applicationInfoArea), labelFont);
        row = addFormField(form, gbc, row, "Website / Information", websiteField, labelFont);

        GridBagConstraints filler = new GridBagConstraints();
        filler.gridx = 0;
        filler.gridy = row;
        filler.gridwidth = 2;
        filler.weighty = 1;
        filler.fill = GridBagConstraints.VERTICAL;
        form.add(Box.createVerticalGlue(), filler);

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setPreferredSize(new Dimension(760, 610));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JLabel title = new JLabel(" Scheme Builder", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(45, 70, 115));

        JLabel hint = new JLabel(
                "Fill the form below just like creating a user profile. Then save the scheme in one click.",
                SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(new Color(100, 110, 125));

        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.setBackground(new Color(242, 247, 255));
        wrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        wrapper.add(title, BorderLayout.NORTH);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(hint, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(top, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);
        wrapper.add(center, BorderLayout.CENTER);

        int result = showCenteredConfirm(wrapper, " Add New Government Scheme");

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String benefit = benefitField.getText().trim();
        String state = stateField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || description.isEmpty()
                || benefit.isEmpty() || state.isEmpty()) {
            throw new InvalidSchemeException("Please fill all required basic scheme fields.");
        }

        int minAge;
        int maxAge;
        double maxIncome;
        double minMarks;
        double disabilityPercentage;

        try {
            minAge = Integer.parseInt(minAgeField.getText().trim());
            maxAge = Integer.parseInt(maxAgeField.getText().trim());
            maxIncome = Double.parseDouble(maxIncomeField.getText().trim());
            minMarks = Double.parseDouble(minMarksField.getText().trim());
            disabilityPercentage = Double.parseDouble(disabilityField.getText().trim());
        } catch (NumberFormatException ex) {
            throw new InvalidSchemeException(
                    "Please enter valid numeric values for age, income, marks and disability percentage.");
        }

        if (minAge < 0 || maxAge < minAge || maxAge > 120) {
            throw new InvalidSchemeException("Age range must be valid and within 0-120.");
        }
        if (maxIncome < 0) {
            throw new InvalidSchemeException("Maximum income cannot be negative.");
        }
        if (minMarks < 0 || minMarks > 100) {
            throw new InvalidSchemeException("Minimum marks must be between 0 and 100.");
        }
        if (disabilityPercentage < 0 || disabilityPercentage > 100) {
            throw new InvalidSchemeException(
                    "Disability percentage must be between 0 and 100.");
        }

        ArrayList<Document> docs = new ArrayList<>();
        String[] documentLines = documentsArea.getText().split("\\R");
        for (String doc : documentLines) {
            if (!doc.trim().isEmpty()) {
                docs.add(new Document(doc.trim(), true));
            }
        }

        String education = (String) educationBox.getSelectedItem();
        String occupation = occupationField.getText().trim();
        if (occupation.isEmpty()) occupation = "Any";

        String applicationMethod = applicationMethodField.getText().trim();
        String applicationInformation = applicationInfoArea.getText().trim();
        String website = websiteField.getText().trim();

        if (applicationMethod.isEmpty() || applicationInformation.isEmpty()) {
            throw new InvalidSchemeException(
                    "Application method and application information are required.");
        }

        String type = (String) typeBox.getSelectedItem();

        Scheme created;
        if (type.equals("Scholarship")) {
            created = new Scholarship(
                    id, name, description, benefit,
                    (String) genderBox.getSelectedItem(),
                    (String) categoryBox.getSelectedItem(),
                    state,
                    minAge, maxAge, maxIncome,
                    studentBox.isSelected(), minMarks,
                    education, occupation,
                    disabilityBox.isSelected(), disabilityPercentage,
                    farmerBox.isSelected(), docs,
                    applicationMethod, applicationInformation, website);
        } else {
            created = new WelfareScheme(
                    id, name, description, benefit,
                    (String) genderBox.getSelectedItem(),
                    (String) categoryBox.getSelectedItem(),
                    state,
                    minAge, maxAge, maxIncome,
                    studentBox.isSelected(), minMarks,
                    education, occupation,
                    disabilityBox.isSelected(), disabilityPercentage,
                    farmerBox.isSelected(), docs,
                    applicationMethod, applicationInformation, website);
        }
        created.setSchemeCategory((String) schemeCategoryBox.getSelectedItem());
        return created;
    }

    private int addFormField(JPanel form, GridBagConstraints gbc, int row,
                             String label, Component component, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(font(Font.BOLD, 12));
        fieldLabel.setForeground(DARK_TEXT);
        form.add(fieldLabel, (GridBagConstraints) gbc.clone());

        styleFormComponent(component);
        GridBagConstraints value = (GridBagConstraints) gbc.clone();
        value.gridx = 1;
        value.weightx = 1;
        value.fill = GridBagConstraints.HORIZONTAL;
        form.add(component, value);
        return row + 1;
    }

    private void addFormSection(JPanel form, GridBagConstraints gbc,
                                int row, String title) {
        GridBagConstraints section = (GridBagConstraints) gbc.clone();
        section.gridx = 0;
        section.gridy = row;
        section.gridwidth = 2;
        section.weightx = 1;
        section.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(title);
        label.setFont(font(Font.BOLD, 15));
        label.setForeground(PEACOCK_DARK);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xC9DDDE)),
                BorderFactory.createEmptyBorder(12, 2, 7, 2)));
        form.add(label, section);
    }


    private String choose(String title, String[] values, String selected) {
        JComboBox<String> box = new JComboBox<>(values);
        setSelectedIfPresent(box, selected);

        int result = JOptionPane.showConfirmDialog(
                this, box, title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        return result == JOptionPane.OK_OPTION
                ? (String) box.getSelectedItem() : null;
    }

    private Scheme chooseSchemeFromList(String title) {
        JComboBox<String> box = new JComboBox<>();
        for (Scheme scheme : schemeRepository.getAll()) {
            box.addItem(scheme.getName().toUpperCase()
                    + "  •  " + scheme.getSchemeCategory()
                    + "  •  " + scheme.getType());
        }
        if (box.getItemCount() == 0) {
            showError("No schemes are currently available.");
            return null;
        }
        int result = showCenteredConfirm(box, title);
        if (result != JOptionPane.OK_OPTION) return null;
        return schemeRepository.getAll().get(box.getSelectedIndex());
    }

    private Scheme findSchemeById(String id) throws SchemeNotFoundException {
        for (Scheme scheme : schemeRepository.getAll()) {
            if (scheme.getId().equalsIgnoreCase(id)) {
                return scheme;
            }
        }
        throw new SchemeNotFoundException(
                "Scheme not found for ID: " + id);
    }

    private EligibilityResult runEligibilityCheck(Scheme scheme) {
        return scheme.checkEligibility(currentUser);
    }

    private void requireProfile() throws MissingProfileException {
        if (currentUser == null) {
            throw new MissingProfileException(
                    "Please enter your user profile first.");
        }
    }

    private int showCenteredConfirm(Object message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(this, title);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        Object value = pane.getValue();
        if (value instanceof Integer) return (Integer) value;
        return JOptionPane.CLOSED_OPTION;
    }

    private String showInputCentered(String message, String title, String initialValue) {
        JTextField field = new JTextField(initialValue == null ? "" : initialValue, 24);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        panel.add(new JLabel(message), BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        int result = showCenteredConfirm(panel, title);
        return result == JOptionPane.OK_OPTION ? field.getText() : null;
    }

    private int showCenteredYesNo(String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = pane.createDialog(this, title);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        Object value = pane.getValue();
        return value instanceof Integer ? (Integer) value : JOptionPane.CLOSED_OPTION;
    }

    private void showMessageCentered(String title, Object message, int messageType) {
        JOptionPane pane = new JOptionPane(message, messageType, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = pane.createDialog(this, title);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showText(String title, String content) {
        JEditorPane editor = new JEditorPane();
        editor.setContentType("text/html");
        editor.setEditable(false);
        editor.setBackground(Color.WHITE);
        editor.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        editor.setText(reportToHtml(content));
        editor.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setPreferredSize(new Dimension(820, 590));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        showMessageCentered(title, scrollPane, JOptionPane.INFORMATION_MESSAGE);
    }

    private String reportToHtml(String content) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>")
            .append("body{font-family:'Segoe UI';font-size:13px;color:#45515f;margin:8px;}")
            .append(".title{font-size:20px;font-weight:bold;font-style:italic;color:#233750;margin:8px 0 14px;}")
            .append(".section{font-size:14px;font-weight:bold;font-style:italic;color:#315f82;margin-top:14px;margin-bottom:6px;}")
            .append(".scheme{font-size:17px;font-weight:bold;font-style:italic;text-transform:uppercase;color:#1f4f6f;margin-top:16px;}")
            .append(".good{color:#2d8255;font-weight:bold;}")
            .append(".bad{color:#b9464c;font-weight:bold;}")
            .append(".normal{line-height:1.45;margin:3px 0;}")
            .append("</style></head><body>");
        String[] lines = content.split("\\R");
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) { html.append("<div style='height:5px'></div>"); continue; }
            String esc = escapeHtml(line);
            if (line.startsWith("===") || line.startsWith("---")) continue;
            if (line.matches("\\d+\\. .+")) {
                html.append("<div class='scheme'>").append(esc).append("</div>");
            } else if (line.startsWith("✓")) {
                html.append("<div class='good'>").append(esc).append("</div>");
            } else if (line.startsWith("✗")) {
                html.append("<div class='bad'>").append(esc).append("</div>");
            } else if (line.endsWith(":") || (line.equals(line.toUpperCase()) && line.length() > 5)) {
                html.append("<div class='section'>").append(esc).append("</div>");
            } else {
                html.append("<div class='normal'>").append(esc).append("</div>");
            }
        }
        html.append("</body></html>");
        return html.toString();
    }

    // Method overloading retained from the original project.
    private void showMessage(String message) {
        showMessageCentered("Message", message, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessage(String title, String message) {
        showMessageCentered(title, message, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        showMessageCentered("Input / Operation Error", message, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info :
                    UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GovernmentSchemePortal app =
                        new GovernmentSchemePortal();
                app.setVisible(true);
            }
        });
    }
}
