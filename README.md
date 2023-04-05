# Course_Management_System

- Written in Java

- The code consists of two interfaces: Person and Service. Admin and Student
are the two kinds of users in the program. They have been modelled via
classes that implement the Person interface. They each contain the
respective attributes that define the user (for example, Admin contains
attributes like qualifications and research_interest, Student contains
attributes like ID) and of course the getter and setter methods for the
same.

- Next, there are AdminService and StudentService classes, which
implement the Service interface, as well as extend Admin and Student
classes respectively (so that they have access to the necessary attributes).
These Service classes contain all the methods that enable the users to
perform a variety of functions.



- The Admin, once logged in, has access to the following functionalities:

  0. View Your Profile
  1. Edit Your Profile
  2. View Course Details
  3. Edit Course Details
  4. View Profile of a Student
  5. Enroll a Student
   6. Unenroll a Student
  7. Insert Marks/Edit Marksheet
  8. View Marksheet
  9. Put up Course Content/Notice/Announcement
  10. Check messages
  11. View all Students currently enrolled
  12. Set up New Password
  13. Check and Take Action on Enrollment Requests
  14. Update Student Records
  15. View Total Students Currently Enrolled
  16. Logout


- Similarly, a Student can do the following:

  0. Set up New Password
  1. View Marksheet
  2. Unenroll from the Course (A notification will be sent to
      the Professor)
  3. Send message to Admin
  4. Check Notices/Course Content
  5. View Professor's Profile
  6. View Course Details
  7. View Total Students Currently Enrolled
  8. Logout



- As you may also tell, it is entirely a menu-driven program.

- In addition to the above, the code also has a Course class that contains
attributes about the course, which are to be entered by the admin during
first log-in, and can also be modified later on.

- Of course, there is also the main class (titled
“Course_Management”) that contains the main() method itself.
Throughout the project, data has been stored making extensive use of
.txt files. What this enables the coder to do is to store multiple, multilined data in
pure String format by using Writer and OutputStream classes, and then
retrieve them using the Reader, Scanner, and InputStream classes.

- The files are titled Admin.txt, Course.txt and Students.txt that store the
details of the Admin, Course and Students respectively. There are also
CourseContent.txt and YourMessages.txt, that contain every notice put up
by the Admin and the direct messages sent by the Students respectively,
along with their date-time stamps and a “READ”/“UNREAD” or “NEW” tag.

- Additionally, there is a Marks.txt file that stores the marks of every
student in a comma separated row-by-row format. All these files are open
to updation (from the code). In fact, all of the methods open one or two of
these files and perform CRUD operations on it: Creating, Reading, Updating or
Deleting.
