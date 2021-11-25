import java.util.*;
import javax.lang.model.util.ElementScanner6;

import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

interface Person {
    void set_name(String n);

    void set_age(int a);

    void set_gender(String g);

    String get_name();

    String get_gender();

    int get_age();
}

interface Service {
    public String []columns = {"ID", "Tutorial-Test-1", "Tutorial-Test-2", "Tutorial-Test-3", "Tutorial-Test-4", "Tutorial-Test-5", "Tutorial-Test-6", "Midsemester Exam", "Comprehensive Exam", "Project"};	
    public String []short_columns = {"ID", "Tut-1", "Tut-2", "Tut-3", "Tut-4", "Tut-5", "Tut-6", "Mid-Sem", "End-Sem", "Project"};
    void view_marksheet(int i);
    void unenroll(String n, int i);
    void set_password();
    void view_all();
    void batch_strength();
}

class password {
    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

class Student implements Person {
    private String name;
    private String gender;
    private int id;
    private int age;
    //private ArrayList<Integer> marks_total;

    public void set_name(String n) {
        name = n;
    }

    public void set_id(int i) {
        id = i;
    }

    public void set_age(int a) {
        age = a;
    }

    public void set_gender(String g) {
        gender = g;
    }

    public String get_name() {
        return name;
    }

    public int get_id() {
        return id;
    }

    public String get_gender() {
        return gender;
    }

    public int get_age() {
        return age;
    }

}

class Admin implements Person {
    private int num_students;
    private String name;
    private String qualification;
    private String research_interest;
    private int age;
    private String gender;

    public void set_name(String n) {
        name = n;
    }

    public void set_qualification(String q) {
        qualification = q;
    }

    public void set_age(int a) {
        age = a;
    }

    public void set_research_interest(String ri) {
        research_interest = ri;
    }

    public void set_gender(String g) {
        gender = g;
    }

    public String get_name() {
        return name;
    }

    public String get_qualification() {
        return qualification;
    }

    public String get_research_interest() {
        return research_interest;
    }

    public int get_age() {
        return age;
    }

    public String get_gender() {
        return gender;
    }

}

class Course {
    private String name;
    private String code;
    private String desc; // this will hold a single-line description
    private String prereqs;
    private String[] TAs;
    
//    Course() {
//    
//    }
    
    public String get_name() {
    	return name;
    }
    public String get_code() {
    	return code;
    }
    public String get_desc() {
    	return desc;
    }
    public String get_prereqs() {
    	return prereqs;
    }
    public String[] get_TAs() {
    	return TAs;
    }
    public void set_name(String name) {
    	this.name = name;
    }
    public void set_code(String code) {
    	this.code = code;
    }
    public void set_desc(String desc) {
    	this.desc = desc;
    }
    public void set_prereqs(String prereqs) {
    	this.prereqs = prereqs;
    }
    public void set_TAs(String[] TAs) {
    	this.TAs = TAs;
    }
}

class StudentService extends Student implements Service {

    Scanner sc = new Scanner(System.in);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy \n HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String dt=dtf.format(now);

    public void get_info(int i) {
        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            String n[] = new String[4];
            while ((lin = br.readLine()) != null) {
                if (lin.contains(","+Integer.toString(i)+","))
                    n = lin.split(",");
            }
            System.out.println("Welcome " + n[0] + "...!");
            fr.close();
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void view_marksheet(int i) 
    {
		try
		{
    		FileInputStream fis = new FileInputStream("Marks.txt");
    		Scanner vmark = new Scanner(fis);
    		String line = "";
    		String marks[] = new String[10];
    		while(vmark.hasNext())
    		{
    			line = vmark.nextLine();
    			if(line.contains(""+i)) 
    			{
    				marks = line.split(",");
    			}
    		}
    		String nm="";
            try {
                File f1 = new File("Students.txt");
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);
                String lin;
                String out[];
                while ((lin = br.readLine()) != null) {
                    if (lin.contains(","+Integer.toString(i)+",")) {
                        out = lin.split(",");
                        nm = out[0];
                    }
                }
                fr.close();
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Your marksheet is as follows: ");
    		System.out.println("Name: "+nm+", ID: "+i);
    		for(int j=1; j<10; j++)
    		{
    			System.out.println(columns[j]+": "+marks[j]);
    		}
    		vmark.close();
		}
		catch(Exception e) {}
	}

    public void unenroll(String n, int i) {

        System.out.println();
        System.out.println("Please enter your password for confirmation:");
        String p = sc.next() + sc.nextLine();
        int flag = 0;
        System.out.println();
        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            while ((lin = br.readLine()) != null) {
                if (lin.contains(password.encrypt(p)))
                    flag = 1;
            }

            fr.close();
            br.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (flag != 1) {
            System.out.println("Wrong Password entered..!");
            System.out.println();
            return;
        }
        Vector<String> lines = new Vector<String>();
        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            while ((lin = br.readLine()) != null) {
                if (!(lin.contains(n+",") || lin.contains(","+Integer.toString(i)+",")))
                    lines.add(lin);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);

            Iterator it = lines.iterator();

            while (it.hasNext()) {
                out.write(it.next() + "\n");
            }

            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
        System.out.println("You have Unenrolled from the Course...!");
        System.out.println();

    }

    public void send_message(Vector<String> m, String i) {
        try {
            FileWriter fw = new FileWriter("YourMessages.txt", true);
            BufferedWriter out = new BufferedWriter(fw);
            out.write("___UN-READ___" + "\n");

            out.write("----ID: " + i + " ----" + "\n");
            // for (String line : m) {
            // out.write(line + "\n");
            // }

            Iterator it = m.iterator();

            while (it.hasNext()) {
                out.write(it.next() + "\n");
            }

            out.write("-------------------------------\n");
            out.write("\n");
            out.flush();
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void check_course_content() {
        File fileM = new File("CourseContent.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileM));
            String line = reader.readLine();
            System.out.println(line);
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
                if (line != null)
                    System.out.println(line);
            }
            String newContent = oldContent.replaceAll("___NEW___", "_________");
            writer = new FileWriter(fileM);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send_enroll_request(int i) {
        System.out.println();
        System.out.println("Enter Name:");
        String nm = sc.next() + sc.nextLine();
        System.out.println();

        // System.out.println("Enter ID:");
        // String id = sc.next();
        // System.out.println();

        System.out.println("Enter Age:");
        String ag = sc.next();
        System.out.println();

        System.out.println("Enter Gender:");
        String gd = sc.next()+sc.nextLine();
        System.out.println();

        System.out.println("Enter CG:");
        String cg = sc.next();
        System.out.println();

        System.out.println("Enter Academic Interests (seperate by comma):");
        String in = sc.next() + sc.nextLine();
        System.out.println();

        try {
            FileWriter fw = new FileWriter("EnrollReq.txt", true);
            BufferedWriter out = new BufferedWriter(fw);
            // for (String line : m) {
            // out.write(line + "\n");
            // }
            //out.write("["+dt+"]"+"\n");
            out.write("\n");
            out.write(nm + ";");
            out.write(Integer.toString(i) + ";");
            out.write(ag + ";");
            out.write(gd + ";");
            out.write(cg + ";");
            out.write(in + "\n");
            out.flush();
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void view_all() {
        try {
            FileInputStream fis = new FileInputStream("Students.txt");
            Scanner fsc = new Scanner(fis);
            int j = 1;
            String line[] = new String[4];
            System.out.println("\tID\tAGE\tGENDER\tNAME");
            System.out.println("-------------------------------------------------");
            System.out.println();
            while (fsc.hasNextLine()) {
                line = fsc.nextLine().split(",");
                System.out.print(j+".\t");
                System.out.print(line[1] + "\t");
                System.out.print(line[2] + "\t");
                System.out.print(line[3] + "\t");
                System.out.print(line[0] + "\t\n");
                System.out.println("----------------------------------------------------------");
                System.out.println();
                j++;

            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void batch_strength()
    {
        int j = 0;
        try {
            FileInputStream fis = new FileInputStream("Students.txt");
            Scanner fsc = new Scanner(fis);
            String line[] = new String[4];
            System.out.println();
            while (fsc.hasNextLine()) {
                line = fsc.nextLine().split(",");
                j++;
            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        System.out.println("Total Students Enrolled: "+j);
        System.out.println();
    }

    public void set_password() {

        System.out.println("Enter ID:");
        int i = sc.nextInt();
        System.out.println();
        System.out.println("Enter Old Password:");
        String op = sc.next() + sc.nextLine();
        System.out.println();
        System.out.println("Enter New Password (min. 6 characters):");
        String p = sc.next() + sc.nextLine();
        System.out.println();
        int flag=0;
        Vector<String> lines = new Vector<String>();
        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            while ((lin = br.readLine()) != null) {
                if (lin.contains(","+Integer.toString(i)+",") && lin.contains(password.encrypt(op)))
                    {
                        lin = lin.replace(password.encrypt(op), password.encrypt(p));
                        flag=1;
                    }
                lines.add(lin);
            }

            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            Iterator it = lines.iterator();

            while (it.hasNext()) {
                out.write(it.next() + "\n");
            }
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
        if (flag==1)
        System.out.println("New Password set succesfully..!");
        else
        System.out.println("Wrong Password entered...!");
    }

}

class AdminService extends Admin implements Service {
    
    Scanner sc = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void get_info_admin(int ch) {
        if (ch == 1) {
            System.out.println("-------------------------------");
            System.out.println();

            System.out.println("Please enter your Name:");
            String n = sc.nextLine();

            System.out.println("Please enter your Gender:");
            String g = sc.nextLine();

            System.out.println("Please enter your age:");
            int a = sc.nextInt();

            System.out.println("Please enter your Qualifications:");
            String q = sc.next() + sc.nextLine();

            System.out.println("Please enter your Research Interests:");
            String r = sc.next() + sc.nextLine();

            System.out.println("Please Enter your Password (min. 6 characters):");
            String p = sc.next() + sc.nextLine();

            super.set_name(n);
            super.set_qualification(q);
            super.set_age(a);
            super.set_research_interest(r);
            super.set_gender(g);

            try {
                FileWriter fw = new FileWriter("Admin.txt");

                fw.write(super.get_name() + "\n");
                fw.write(super.get_gender() + "\n");
                fw.write(Integer.toString(super.get_age()) + "\n");
                fw.write(super.get_qualification() + "\n");
                fw.write(super.get_research_interest() + "\n");
                fw.write(password.encrypt(p));

                fw.close();

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }
        } else if (ch == 2) {
            try {
                FileInputStream fis = new FileInputStream("Admin.txt");
                Scanner fsc = new Scanner(fis);
                System.out.println("Name: Prof " + fsc.nextLine());
                System.out.println("Gender: " + fsc.nextLine());
                System.out.println("Age: " + fsc.nextLine());
                System.out.println("Qualifications: " + fsc.nextLine());
                System.out.println("Research Interests: " + fsc.nextLine());
                System.out.println();
                System.out.println("--------------------------------------------");
                fis.close();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }
        }
    }

    public void get_info_course(int ch) {
    	if(ch == 1)
    	{
            System.out.println("-------------------------------");
            System.out.println();

            Course course = new Course();
            
            System.out.println("Please enter the title of this course:");
            String title = sc.nextLine();
            System.out.println();

            System.out.println("Please enter the course code as per university bulletin:");
            String code = sc.nextLine();
            System.out.println();

            System.out.println("Please enter a one-line description of the course:");
            String descr = sc.nextLine();
            System.out.println();
            
            System.out.println("Please enter the expected pre-requisites for the course (in one line):");
            String prereqs = sc.nextLine();
            System.out.println();
            
            System.out.println("Please enter the names of the Teaching Assistants (TA's) for this course (separated by commas):");
            String TAs = sc.nextLine();
            System.out.println();
            
            course.set_name(title);
            course.set_code(code);
            course.set_desc(descr);
            course.set_prereqs(prereqs);
            course.set_TAs(TAs.split(","));
            
            try {
                FileWriter fw = new FileWriter("Course.txt");
                fw.write(course.get_code() + "\n");
                fw.write(course.get_name() + "\n");
                fw.write(course.get_desc() + "\n");
                fw.write(course.get_prereqs() + "\n");
                for(int t = 0; t < course.get_TAs().length; t++)
                {
                	if(t != course.get_TAs().length-1)
                		fw.write(course.get_TAs()[t] + ", ");
                	else
                		fw.write(course.get_TAs()[t] + ".");
                }
                fw.close();

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }
    	}
    	else
    	{
            try {
                FileInputStream fis = new FileInputStream("Course.txt");
                FileInputStream fas = new FileInputStream("Admin.txt");
                Scanner fsc = new Scanner(fis);
                Scanner asc = new Scanner(fas);
                System.out.println();
                System.out.print(fsc.nextLine() + ": ");
                System.out.println(fsc.nextLine().toUpperCase() + "\n");
                System.out.println(fsc.nextLine());
                System.out.println("\nPre-requisites: " + fsc.nextLine());
                System.out.println("Instructor-in-charge: Prof " + asc.nextLine()); asc.close();
                System.out.println("Teaching Assistants: " + fsc.nextLine());
                System.out.println();
                System.out.println("--------------------------------------------");
                fis.close();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }    		
    	}
    }

    public void get_info_student() {

        System.out.println();
        System.out.println("Enter ID to get info of Student");
        int i = sc.nextInt(); int flag=0;

        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            String out[];
            while ((lin = br.readLine()) != null) {
                if (lin.contains(","+Integer.toString(i)+",")) {
                    out = lin.split(",");
                    System.out.println();
                    System.out.println("Name:" + out[0]);
                    System.out.println("ID:" + out[1]);
                    System.out.println("Age:" + out[2]);
                    System.out.println("Gender:" + out[3]);
                    flag=1;
                }

            }
            
            System.out.println();
            view_marksheet(i);
            fr.close();
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(flag==0)
        System.out.println("No such Record Found..!");
        System.out.println();
        System.out.println("--------------------------------------------");

    }

    public void enroll() throws IOException {

        String inp[];
        inp = br.readLine().split(",");
        System.out.println();
        Student st = new Student();
        st.set_name(inp[0]);
        st.set_id(Integer.parseInt(inp[1]));
        st.set_age(Integer.parseInt(inp[2]));
        st.set_gender(inp[3]);

        try {
            FileWriter fw = new FileWriter("Students.txt", true);

            fw.write(st.get_name() + ",");
            fw.write(Integer.toString(st.get_id()) + ",");
            fw.write(Integer.toString(st.get_age()) + ",");
            fw.write(st.get_gender() + ",");
            fw.write(password.encrypt(inp[4]) + "\n");

            fw.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        try {
            FileWriter fw2 = new FileWriter("Marks.txt", true);
            fw2.write(Integer.toString(st.get_id()) + ",");
            fw2.write("0,0,0,0,0,0,0,0,0\n");
            fw2.close();
     
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

    }

    public void enroll(String nm, String id, String ag, String gd, String pw) {

        Student st = new Student();
        st.set_name(nm);
        st.set_id(Integer.parseInt(id));
        st.set_age(Integer.parseInt(ag));
        st.set_gender(gd);

        try {
            FileWriter fw = new FileWriter("Students.txt", true);

            fw.write(st.get_name() + ",");
            fw.write(Integer.toString(st.get_id()) + ",");
            fw.write(Integer.toString(st.get_age()) + ",");
            fw.write(st.get_gender() + ",");
            fw.write(password.encrypt(pw)+ "\n");

            fw.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        try {
            FileWriter fw2 = new FileWriter("Marks.txt", true);
            fw2.write(Integer.toString(st.get_id()) + ",");
            fw2.write("0,0,0,0,0,0,0,0,0\n");
            fw2.close();
     
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void unenroll(String n, int i) {
        Vector<String> lines = new Vector<String>();
        int flag=0;
        try {
            File f1 = new File("Students.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin; 
            while ((lin = br.readLine()) != null) {
                if (lin.contains(n+",") && lin.contains(","+Integer.toString(i)+","))
                {
                    flag=1;
                }
                else
                	lines.add(lin);
            }

            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);

            Iterator it = lines.iterator();

            while (it.hasNext()) {
                out.write(it.next() + "\n");
            }

            out.flush();
            out.close();
        
        File fileM = new File("Marks.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        reader = new BufferedReader(new FileReader(fileM));
        String ln;
        String del = "";
        while ((ln = reader.readLine()) != null) {
            if (!ln.contains(""+i))
            {
                oldContent = oldContent + ln + System.lineSeparator();
            }
            else
            {
                oldContent = oldContent + ln;
                del = ln;
                flag=1;
            }
        }
        String newContent = oldContent.replaceAll(del, "");
        writer = new FileWriter(fileM);
        writer.write(newContent);
        reader.close();
        writer.close();
        System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(flag==1)
        System.out.println("Student: " + n + " with ID: " + i + " has been unenrolled from the Course.");
        else
        System.out.println("No such Record found..!");
    }

    public void edit_marksheet(int i1, int i2) {
    	if(i1 == 1)
    	{
    		String[] new_marks = new String[10];
    		File fileM = new File("Marks.txt");
    		String oldContent = "";
            BufferedReader reader = null;
            FileWriter writer = null;
            System.out.println();
    		try {
                reader = new BufferedReader(new FileReader(fileM));
                String ln;
                String del = "";
                while ((ln = reader.readLine()) != null) {
                    oldContent = oldContent + ln + System.lineSeparator();
                    if (ln.contains(Integer.toString(i2)+","))
                    {
                        del = ln;
                    }
                }
                String[] mark_arr = del.split(",");
    			System.out.println("As per the current records, the marks of the student bearing ID " + i2 + " are as follows: ");
    			System.out.println();
                for(int i=1; i<mark_arr.length; i++)
    				System.out.println(columns[i] + ": " + mark_arr[i]);
    			char choice = 'N';
                System.out.println();
    			while(Character.toUpperCase(choice) == 'N')
    			{
    				System.out.println("Insert the marks for the given student of all the above components (in order) as space-separated values.");
    				new_marks = sc.nextLine().split(" ");
    				System.out.println("Are you sure? (Y/N)");
    				choice = sc.nextLine().charAt(0);
    			}
    			String nms = "" + i2;
    			for (String m : new_marks)
    			{
    				nms += "," + m;
    			}
                String newContent = oldContent.replaceAll(del, nms);
                writer = new FileWriter(fileM);
                writer.write(newContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                	System.out.println("Marks updated successfully.");
                    try {
                        reader.close();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            System.out.println();
    	}
    	else if(i1 == 2)
    	{
    		String[] new_marks = new String[10];
    		File fileM = new File("Marks.txt");
    		System.out.println("You are presently inserting/editing the marks of all students for the component you chose.");
    		System.out.println();
            BufferedReader reader = null;
            FileWriter writer = null;
    		try {
                reader = new BufferedReader(new FileReader(fileM));
                String ln;
                String del = "";
                String nms = "";
    			char choice = 'N';
    			while(Character.toUpperCase(choice) == 'N')
    			{
    				System.out.println("Please enter, in one line with space-separated values, marks secured in the chosen component by all students in order of their ID as per records: ");
    				new_marks = sc.nextLine().split(" ");
    				System.out.println("Are you sure? (Y/N)");
    				choice = sc.nextLine().charAt(0);
    			}
    			int c = 0;
                while ((ln = reader.readLine()) != null) {
                    del = ln;
                    String[] mark_arr = del.split(",");
                    nms += mark_arr[0];
                    for(int k=1; k<columns.length; k++)
                    {
                    	if(k==i2)
                    		nms += "," + new_marks[c];
                    	else
                    		nms += "," + mark_arr[k];
                    }
                    c++;
                    nms += System.lineSeparator();
                }
                writer = new FileWriter(fileM);
                writer.write(nms);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                	System.out.println("Marks updated successfully.");
                    try {
                        reader.close();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            System.out.println();
    	}
    }

    public void view_marksheet(int i){
    	if(i == 0)
    	{
    		try
    		{
	    		System.out.println("Following are the marks of each student in every component: ");
                
                System.out.println();
	    		FileInputStream fis = new FileInputStream("Marks.txt");
	    		Scanner vmark = new Scanner(fis);
	    		String line = "";
	    		for(int ic=0; ic<10; ic++)
	    			System.out.print(short_columns[ic]+"\t");
	    		System.out.println();
	    		while(vmark.hasNext())
	    		{
	    			line = vmark.nextLine();
	    			line = line.replace(",", "\t");
	    			System.out.println(line);
	    		}
    		}
    		catch(Exception e) {}
    	}
    	else
    	{
    		try
    		{
	    		FileInputStream fis = new FileInputStream("Marks.txt");
	    		Scanner vmark = new Scanner(fis);
	    		String line = "";
	    		String marks[] = new String[10];
	    		while(vmark.hasNext())
	    		{
	    			line = vmark.nextLine();
	    			if(line.contains(""+i+",")) 
	    			{
	    				marks = line.split(",");
	    			}
	    		}
	    		String nm="";
	            try {
	                File f1 = new File("Students.txt");
	                FileReader fr = new FileReader(f1);
	                BufferedReader br = new BufferedReader(fr);
	                String lin;
	                String out[];
	                while ((lin = br.readLine()) != null) {
	                    if (lin.contains(","+Integer.toString(i)+",")) {
	                        out = lin.split(",");
	                        nm = out[0];
	                    }
	                }
	                fr.close();
	                br.close();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	            System.out.println("The requested marksheet is as follows: ");
                System.out.println();
	    		System.out.println("Name: "+nm+", ID: "+i);
                
                System.out.println();
	    		for(int j=1; j<10; j++)
	    		{
	    			System.out.println(columns[j]+": "+marks[j]);
	    		}
	    		vmark.close();
    		}
    		catch(Exception e) {}
    	}
    }

    public void put_course_content(Vector<String> cn) {
        try {
            FileWriter fw = new FileWriter("CourseContent.txt", true);
            BufferedWriter out = new BufferedWriter(fw);
            out.write("___NEW___" + "\n");
            for (String line : cn) {
                out.write(line + "\n");
            }

            out.write("-------------------------------\n");
            out.write("\n");
            out.flush();
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void check_messages() {
        File fileM = new File("YourMessages.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileM));
            String line = reader.readLine();
            System.out.println(line);
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
                if (line != null)
                    System.out.println(line);
            }
            String newContent = oldContent.replaceAll("___UN-READ___", "___READ___");
            writer = new FileWriter(fileM);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void view_all() {
        try {
            FileInputStream fis = new FileInputStream("Students.txt");
            Scanner fsc = new Scanner(fis);
            int j = 1;
            String line[] = new String[4];
            System.out.println("\tID\tAGE\tGENDER\tNAME");
            System.out.println("-------------------------------------------------");
            System.out.println();
            while (fsc.hasNextLine()) {
                line = fsc.nextLine().split(",");
                System.out.print(j+".\t");
                System.out.print(line[1] + "\t");
                System.out.print(line[2] + "\t");
                System.out.print(line[3] + "\t");
                System.out.print(line[0] + "\t\n");
                System.out.println("----------------------------------------------------------");
                System.out.println();
                j++;

            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void enroll_requests() {
        try {
            FileInputStream fis = new FileInputStream("EnrollReq.txt");
            Scanner fsc = new Scanner(fis);
            int j = 0;
            String line[] = new String[6];

            System.out.println();
            System.out.println("1. View all Requests");
            System.out.println("2. Take Action on them");
            int ch = sc.nextInt();

            if (ch == 1) {
                while (fsc.hasNextLine()) {
                    line = fsc.nextLine().split(";");
                    if (line[0].equalsIgnoreCase("----") || line[0].equalsIgnoreCase("") || line[0].equalsIgnoreCase(" ") || line[0].equalsIgnoreCase("\n"))
                        continue;
                    else{   
                        System.out.println("Name: " + line[0]);
                        System.out.println("ID: " + line[1]);
                        System.out.println("Age: " + line[2]);
                        System.out.println("Gender: " + line[3]);
                        System.out.println("CG: " + line[4]);
                        System.out.println("Academic Interests: " + line[5]);
                        System.out.println("---------------------");
                        System.out.println();
                    }
                }
            } else if (ch == 2) {
                while (fsc.hasNextLine()) {
                    line = fsc.nextLine().split(";");
                    if (line[0].equalsIgnoreCase("----") || line[0].equalsIgnoreCase("") || line[0].equalsIgnoreCase(" "))
                        continue;
                    System.out.println("Name: " + line[0]);
                    System.out.println("ID: " + line[1]);
                    System.out.println("Age: " + line[2]);
                    System.out.println("Gender: " + line[3]);
                    System.out.println("CG: " + line[4]);
                    System.out.println("Academic Interests: " + line[5]);
                    System.out.println("---------------------");
                    System.out.println();

                    System.out.println("1. Accept Enrollment Request");
                    System.out.println("2. Reject Enrollment Request");

                    int choice = sc.nextInt();
                    System.out.println();
                    if (choice == 1) {
                        System.out.println("Enter Preliminary Password");
                        String pw = sc.next();
                        enroll(line[0], line[1], line[2], line[3], pw);
                    }

                    File fileM = new File("EnrollReq.txt");
                    String oldContent = "";
                    BufferedReader reader = null;
                    FileWriter writer = null;
                    try {
                        reader = new BufferedReader(new FileReader(fileM));
                        String ln;
                        String del = "";
                        while ((ln = reader.readLine()) != null) {
                            oldContent = oldContent + ln + System.lineSeparator();
                            if (ln.contains(line[0]+";"))
                                del = ln;
                        }
                        String newContent = oldContent.replaceAll(del, "----");
                        writer = new FileWriter(fileM);

                        writer.write(newContent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("There are no enrollment requests as of yet.");
        }
    }

    public void update_record() throws IOException
    {

        System.out.println();
        String n="";
        System.out.println("1. Search by Name. ");
        System.out.println("2. Search by ID");
        int sb=sc.nextInt();
        System.out.println();
        if(sb==1)
        {
            n=sc.next()+sc.nextLine();
        }
        else if(sb==2)
        {
            n=sc.next();
        }

        File fileM = new File("Students.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null; int flag=0;
        try {
            reader = new BufferedReader(new FileReader(fileM));
            String ln;
            String del = "";
            while ((ln = reader.readLine()) != null) {
                oldContent = oldContent + ln + System.lineSeparator();
                if (ln.contains(n+","))
                {
                    del = ln;
                    flag=1;
                }
            }
        }
        catch(Exception e)
        {
        }
        if(flag==0)
        {
            System.out.println("No such Record found..!");
            return;
        }
        System.out.println("Enter new record (Name, ID, Age, Gender, Password [New]) (Comma Seperated)");

        String inp[];
        inp=br.readLine().split(",");
      
        try {
            reader = new BufferedReader(new FileReader(fileM));
            String ln;
            String del = "";
            while ((ln = reader.readLine()) != null) {
                oldContent = oldContent + ln + System.lineSeparator();
                if (ln.contains(n+","))
                {
                    del = ln;
                    flag=1;
                }
            }
            String newContent = oldContent.replaceAll(del, inp[0]+","+inp[1]+","+inp[2]+","+inp[3]+","+password.encrypt(inp[4]));
            writer = new FileWriter(fileM);

            writer.write(newContent);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        System.out.println();
        if(flag==0)
        System.out.println("No such record found..!");
        else
        System.out.println("Record Updated Successfully..!");
    }

    public void batch_strength()
    {
        int j = 0;
        try {
            FileInputStream fis = new FileInputStream("Students.txt");
            Scanner fsc = new Scanner(fis);
 
            String line[] = new String[4];
            System.out.println();
            while (fsc.hasNextLine()) {
                line = fsc.nextLine().split(",");
                j++;
            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        System.out.println("Total Students Enrolled: "+j);
        System.out.println();
    }

    public void set_password() {

        System.out.println("Enter Old Password:");
        String op = sc.next() + sc.nextLine();

        System.out.println("Enter New Password (min. 6 characters):");
        String p = sc.next() + sc.nextLine();

        List<String> lines = new ArrayList<String>();
        try {
            File f1 = new File("Admin.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String lin;
            while ((lin = br.readLine()) != null) {
                if (lin.contains(password.encrypt(op)))
                    lin = lin.replace(password.encrypt(op), password.encrypt(p));
                lines.add(lin);
            }

            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for (String s : lines)
                out.write(s + "\n");
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
        System.out.println("New Password set succesfully..!");
    }

}

public class Course_Management {
//Main Class From where everything will be operated
    public static void main(String args[]) throws IOException{
        Scanner sc = new Scanner(System.in);

        try {
            FileInputStream fis = new FileInputStream("Course.txt");
            Scanner fsc = new Scanner(fis);
            System.out.println();
            String code=fsc.nextLine();
            String c_name=fsc.nextLine().toUpperCase();

            System.out.println("WELCOME TO "+code+": "+"("+c_name+")");
        }
        catch(Exception e)
        {
            //System.out.println(e);
        }
        
        System.out.println("---------------------------------------");
        System.out.println();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy \n HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt=dtf.format(now);

        AdminService as = new AdminService();
        StudentService ss = new StudentService();

        int choice = 1;
        do {
            System.out.println("1. Admin Functions and Control");
            System.out.println("2. Student Functions");
            System.out.println("---------------------------------------");
            System.out.println();

            int ch = sc.nextInt();

            if (ch == 1) {
                int admin_choice=0; int case_16=0;
                
                System.out.println("Welcome Admin...!");
                System.out.println("-------------------");
                System.out.println();
                String chc = "";
                do {
                    System.out.println("1. Set up Your Account");
                    System.out.println("2. Login");
                    System.out.println("-------------------------------------------");
                    System.out.println();

                    chc = sc.next();
                } while (!(chc.equals("1") || chc.equals("2")));
                String pw = "";
                if (Integer.parseInt(chc) == 1)
                {
                    as.get_info_admin(Integer.parseInt(chc));
                    as.get_info_course(Integer.parseInt(chc));
                }
                else {
                    try {
                        FileInputStream fis = new FileInputStream("Admin.txt");
                        Scanner fsc = new Scanner(fis);

                        for (int i = 1; i <= 5; i++) {
                            pw = fsc.nextLine();
                        }
                        pw = fsc.nextLine();
                        fis.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e);
                    }
                    int flag=0; int c=0;
                    do{
                    System.out.println("Please Enter your Password:");
                    String pc = sc.next() + sc.nextLine();
                    c+=1;
                    if (password.encrypt(pc).equalsIgnoreCase(pw)) {
                        System.out.println(); flag=1;
                        as.get_info_admin(Integer.parseInt(chc));
                    } else {
                        System.out.println();
                        System.out.println("Wrong Password...!");
                        if(c==3)
                        System.exit(0);
                        System.out.println("You will be directed out after "+(3-c)+" attempts");
                        
                    }
                }while(flag==0);
                }
                do{
                System.out.println("-------------------------------------------");
                System.out.println();

                System.out.println("0. View Your Profile");
                System.out.println("1. Edit Your Profile");
                System.out.println("2. View Course Details");
                System.out.println("3. Edit Course Details");
                System.out.println("4. View Profile of a Student");
                System.out.println("5. Enroll a Student");
                System.out.println("6. Unenroll a Student");
                System.out.println("7. Insert Marks/Edit Marksheet");
                System.out.println("8. View Marksheet");
                System.out.println("9. Put up Course Content/Notice/Announcement");
                System.out.println("10. Check messages");
                System.out.println("11. View all Students currently enrolled");
                System.out.println("12. Set up New Password");
                System.out.println("13. Check and Take Action on Enrollment Requests");
                System.out.println("14. Update Student Records");
                System.out.println("15. View Total Students Currently Enrolled");
                System.out.println("16. Logout");

                System.out.println();
                System.out.println("---------------------------------------------");
                System.out.println();

                ch = sc.nextInt();
                switch (ch) {
                    case 0:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.get_info_admin(2);
                        System.out.println();
                        break;

                    case 1:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.get_info_admin(1);
                        System.out.println();
                        break;
                        
                    case 2:
                        System.out.println();
                        as.get_info_course(2);
                        System.out.println();
                        break;
                        
                    case 3:
                        System.out.println();
                        as.get_info_course(1);
                        System.out.println();
                        break;
                        
                    case 4:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.get_info_student();
                        System.out.println();
                        break;

                    case 5:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("Enter Number of Students to Enroll");
                        int num = sc.nextInt();
                        System.out.println();
                        if(num == 1)
                        	System.out.println("Enter data (Name, ID, Age, Gender, Preliminary Password; as comma-separated values) of the Student to enroll:");
                        else
                        	System.out.println("Enter data (Name, ID, Age, Gender, Preliminary Password; as comma-separated values) of all "+num+" Students to enroll:");
                        int x = 0;
                        while (x < num) {
                            System.out.println();
                            as.enroll();
                            x++;
                        }
                        System.out.println();
                        break;

                    case 6:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("Enter Name and ID respectively to unenroll");
                        String n2 = sc.next() + sc.nextLine();
                        int i2 = sc.nextInt();
                        as.unenroll(n2, i2);
                        break;

                    case 7:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("1. Insert/Edit Marks for a particular Student. ");
                        System.out.println("2. Insert/Edit Marks for all Students in a particular Component");
                        System.out.println();
                        int i3 = sc.nextInt();
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        if(i3 == 1)
                        {
                        	System.out.println("Enter the ID of the student: ");
                        	int i31 = sc.nextInt();
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                        	as.edit_marksheet(1, i31);
                        }
                        else if(i3 == 2)
                        {
                        	System.out.println("Which Component would you like to insert/edit Marks for?");
                        	System.out.println("1-6. Tutorial Test #N");
                        	System.out.println("7. Midsemester Exam");
                        	System.out.println("8. Comprehensive Exam");
                        	System.out.println("9. Project");
                        	int i32 = sc.nextInt();
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                        	as.edit_marksheet(2, i32);
                        }
                        break;

                    case 8:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("0- View entire Marksheet of all Students");
                        System.out.println("ID- View Marksheet of this ID");
                        int i4 = sc.nextInt();
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.view_marksheet(i4);
                        break;

                    case 9:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("Enter The Course Content/Notice. Enter \"#e\" to Finish.");
                        Vector<String> cn = new Vector<String>();
                        String s = " ";
		                cn.add("["+dt+"]"+"\n");
                        while (true) {
                            s = sc.next() + sc.nextLine();
                            if (!(s.equalsIgnoreCase("#e")))
                                cn.add(s);
                            if (s.contains("#e"))
                                break;

                        }
                        int sz = cn.size();
                        cn.set(sz - 1, cn.lastElement().replace("#e", ""));
                        System.out.println();
                        as.put_course_content(cn);
                        break;

                    case 10:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("Your Messages:");
                        System.out.println();

                        as.check_messages();
                        break;

                    case 11:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.view_all();
                        break;

                    case 12:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.set_password();
                        break;

                    case 13:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.enroll_requests();
                        break;

                    case 14:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.update_record();
                        break;
                    
                    case 15:
                        System.out.println();
                        System.out.println("----------------------------------------------------------------");
                        as.batch_strength();
                        break;

                    case 16:
                        case_16=1;
                        break;
                    default:
                        System.out.println("Enter Again");

                        System.out.println();

                }
                System.out.println();
                if(case_16==1)
                break;

                System.out.println("1. Perform More Admin Actions");
                System.out.println("2. Logout");

                admin_choice=sc.nextInt();
                System.out.println();
            }while(admin_choice==1);


            } else {

                System.out.println("Welcome Student...!");
                System.out.println("-------------------");
                System.out.println();
                System.out.println("Please enter your ID:");

                int student_choice=0; int case_8=0;
                int i = sc.nextInt();
                File fileM = new File("Students.txt");
                BufferedReader reader = null;
                boolean studentExists = false;
                try {
                    reader = new BufferedReader(new FileReader(fileM));
                    String ln;
                    while ((ln = reader.readLine()) != null) {
                        if (ln.contains(","+Integer.toString(i)+","))
                        {
                            studentExists = true;
                        }
                    }
                }
                catch(Exception e)
                {
                }
                System.out.println();
                int flag = 0;
                if (!studentExists) {
                	System.out.println("You are not enrolled into this course. Don't be sad! You can still do one of the following: \n");
                	System.out.println("Enter \"info\" to View the details of this Course");
                    System.out.println("Enter \"list\" to View all Students enrolled currently");
                    System.out.println("Enter \"req\" to send an enrollment request to Admin");
                    System.out.println();
                    String l = sc.next();
                    System.out.println();
                    if (l.equalsIgnoreCase("info"))
                    {
                    	as.get_info_course(2);
                    }
                    if (l.equalsIgnoreCase("list")) 
                    {
                        ss.view_all();
                    }
                    if (l.equalsIgnoreCase("req")) 
                    {
                        ss.send_enroll_request(i);
                    }
                } else {
                    do {
                        System.out.println("Please enter your Password");
                        String pl = sc.next() + sc.nextLine();
                        flag = 0;
                        try {
                            File f1 = new File("Students.txt");
                            FileReader fr = new FileReader(f1);
                            BufferedReader br = new BufferedReader(fr);
                            String lin;
                            while ((lin = br.readLine()) != null) {
                                if (lin.contains(pl) || lin.contains(password.encrypt(pl)))
                                    flag = 1;
                            }

                            fr.close();
                            br.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        if (flag == 1) {
                            System.out.println();
                            ss.get_info(i);
                        } else {
                            System.out.println("Wrong Password entered");
                            System.out.println("Please Try Again (Enter 0)");
                            System.out.println("OR");
                            System.out.println("Contact Admin if you have forgot Password (Enter 1)");
                            int em = sc.nextInt();

                            if (em == 0)
                                flag = 0;
                            else {
                                Vector<String> pm = new Vector<String>();
                                pm.add("["+dt+"]"+"\n");
                                pm.add("Sir,");
                                pm.add("I have forgot the Password.");
                                pm.add("Please Provide new Password ASAP");
                                pm.add("Thanks");
                                pm.add("---" + i + "---");
                                ss.send_message(pm, Integer.toString(i));
                                System.out.println("Password Change Request sent to Admin..!");
                                System.out.println();
                                System.exit(0);
                            }
                            System.out.println();
                        }
                    } while (flag == 0);
                    do{
                    System.out.println("-------------------------------------------");
                    System.out.println();
                    System.out.println("0. Set up New Password");
                    System.out.println("1. View Marksheet");
                    System.out.println("2. Unenroll from the Course (A notification will be sent to the Professor)");
                    System.out.println("3. Send message to Admin");
                    System.out.println("4. Check Notices/Course Content");
                    System.out.println("5. View Professor's Profile");
                    System.out.println("6. View Course Details");
                    System.out.println("7. View Total Students Currently Enrolled");
                    System.out.println("8. Logout");

                    System.out.println("-------------------------------------------");
                    System.out.println();

                    int c = sc.nextInt();

                    switch (c) {
                        case 0:
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            ss.set_password();
                            break;
                        case 1:
                            // System.out.println("Enter ID to view your marksheet");
                            // int i1 = sc.nextInt();
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            ss.view_marksheet(i);
                            break;

                        case 2:
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            System.out.println("Enter Name to unenroll from the Course");
                            String n = sc.next() + sc.nextLine();
                            //int i2 = sc.nextInt();

                            Vector<String> ms = new Vector<String>();
	                        ms.add("["+dt+"]"+"\n");
                            ms.add("Respected Professor,");
                            ms.add("I hereby inform you that I have decided to unenroll from the Course.\n");
                            ms.add("Yours faithfully,");
                            ms.add(n);
                            ms.add(Integer.toString(i));
                            ss.send_message(ms, Integer.toString(i));
                            ss.unenroll(n, i);
                            break;

                        case 3:
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            System.out.println("Enter the Message to send. Use \"#e\" to terminate your Message");
                            Vector<String> m = new Vector<String>();
		                    m.add("["+dt+"]"+"\n");
                            while (true) {
                                String s = sc.next() + sc.nextLine();
                                if (!(s.equalsIgnoreCase("#e")))
                                    m.add(s);
                                if (s.contains("#e"))
                                    break;

                            }
                            int s = m.size();
                            m.set(s - 1, m.lastElement().replace("#e", ""));
                            System.out.println();
                            // System.out.println("Enter your ID");
                            // String id = sc.next() + sc.nextLine();
                            ss.send_message(m, Integer.toString(i));
                            break;

                        case 4:
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            System.out.println("Here are the Notices/Course Content");

                            ss.check_course_content();
                            break;

                        case 5:
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            as.get_info_admin(2);
                            break;
                        case 6:
                        	System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            as.get_info_course(2);
                            break;
                        
                        case 7:    
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            ss.batch_strength();
                            break;

                        case 8:
                            case_8=1;
                            break;

                        default:
                            System.out.println("Enter again");
                            System.out.println();

                    }
                    System.out.println();
                    if(case_8==1)
                    break;

                    System.out.println("1. Perform More Student Actions");
                    System.out.println("2. Logout");

                    student_choice=sc.nextInt();
                    System.out.println();
                }while(student_choice==1);
                }

            }
            System.out.println("------------------------------------------");
            System.out.println();
            System.out.println("1. Login as Student or Admin");
            System.out.println("2. Exit");
            choice = sc.nextInt();
            System.out.println();
            System.out.println("------------------------------------------");
        } while (choice == 1);

        System.exit(0);
    }
}



