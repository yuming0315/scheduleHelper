import java.io.*;

public class school_Info {
	public static String s;
	public school_Info()
	{
		new academic();
		s = academic.tq;
		int year=2019,month=3,day=0;
		boolean check = false;
		int start=0,end=0;
		int find=0;
		String input = "";
		int n=0;
		
		File file = new File("data.txt");
		FileWriter w= null;
		try {
			w = new FileWriter(file,false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		  while(start!=s.length()) 
		  {
			  find=s.substring(start).indexOf("��: "); 
			  input=s.substring(start,start+find)+"@";
			  start+=find+3;
			  find=s.substring(start).indexOf("��: ");
			  if(find!=-1) {
				  end=start+find-1;
				  //end�� ���ڰ� �ƴҶ����� �ڷΰ���
				  while((s.charAt(end)>='0'&&s.charAt(end)<='9')||s.charAt(end)==' '
						  ||s.charAt(end)==','||s.charAt(end)=='~'||s.charAt(end)=='��'||s.charAt(end)=='��') 
				  {
					  if((s.charAt(end-1)>='0'&&s.charAt(end-1)<='9')&&((s.charAt(end)=='��'||s.charAt(end)=='��'))) {
						  end-=2;
					  }
					  else if((s.charAt(end)>='0'&&s.charAt(end)<='9')&&s.charAt(end-1)=='/') {
						  break;
					  }
					  else if(!(s.charAt(end-1)>='0'&&s.charAt(end-1)<='9')&&((s.charAt(end)=='��'||s.charAt(end)=='��'))) {
						  break;
					  }
					  else {
					  end--;
					  }
				  }
				  input+=s.substring(start,end+1);
				  start=end+2;		
			  }
			  else {
				  input+=s.substring(start);
				  start=s.length();
			  }	
			  
			  
			//������ ���� �� ���Ͽ� ����
			  String [] data = input.split("@");
			  String daydata;
			  String d="";
				  //��¥�� �Ϸ��϶�
			  if(month==13) {
				  month=1;
			  }
				  int m=data[0].indexOf("��");
				  if(data[0].length()<=2) {
					  day=Integer.parseInt(data[0]);
					  d+=day;
					  if(day<n)
						  month++;
					  n=day;
					  
					  try {
							w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
						} catch (IOException e) {
							e.printStackTrace();
						}	
					  
					  
				  }
				  //"��" "��" �� ������� ��
				  else if(m!=-1) {
					  //���� �ε����� 1�̳� 2�϶��� �̹����� �ƴ� //��
					  //���� �ε����� 1�̳� 2�� �ƴҶ� �̹����� //����
					  
					  if(m>2) {
						  data[1]+=" ����";
						  day=Integer.parseInt(data[0].substring(0,2));
						  d+=day;
						  
						  if(day<n)
							  month++;
						  n=day;
						  
						  try {
								w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
							} catch (IOException e) {
								e.printStackTrace();
							}	
						  
					  }
					  else {
						  data[1]+=" ��";
						  int count=data[0].length()-1;
						  while(data[0].charAt(count)>='1'&&data[0].charAt(count)<='9')
							  count--;
						  day=Integer.parseInt(data[0].substring(count+1));
						  d+=day;
						  
						  if(day<n&&!data[1].equals("��������б� ��"))
							  month++;
						  n=day;
						  
						  try {
								w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
							} catch (IOException e) {
								e.printStackTrace();
							}	
						  
						  if(data[1].equals("�ϰ�����б� ��"))
							  month++;
					  }
				  }			  
				  //�����̳� ��ǥ�� ������
				  else {
					  int a;
					  a=data[0].indexOf(",");
					  //��ǥ�� ����
					  if(a==-1) {
						  //���� �˻�
						  int b = data[0].indexOf("~");
						  //������ ����
						  if(b!=-1) {
							  day=Integer.parseInt(data[0].substring(0,b-1));
							  n=Integer.parseInt(data[0].substring(b+2));
							  
							  int c=day;
							  for(;day<=n;day++) {
								  d+=day+" ";
							  }
							  n=c;
							  
						  }
						  try {
								w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
							} catch (IOException e) {
								e.printStackTrace();
							}						  
					  }
					  //��ǥ�� ����
					  else {
						  //��ǥ �յڷ� ������ ���
						  String s1=data[0].substring(0,a);
						  int b = s1.indexOf("~");
						  //�պκп� ������ �ִ��� üũ
						  //������ ������
						  if(b!=-1) {
							  day=Integer.parseInt(s1.substring(0,b-1));
							  n=Integer.parseInt(s1.substring(b+2));
							  int c=day;
							  for(;day<=n;day++) {
								  d+=day+" ";
							  }
							  n=c;
						  }
						  //������ ���� ��
						  else {
							  day=Integer.parseInt(s1);
							  d+=day;
						  }
						  String s2=data[0].substring(a+2);
						  b=s2.indexOf("~");
						  
						  if(b!=-1) {
							  day=Integer.parseInt(s2.substring(0,b-1));
							  n=Integer.parseInt(s2.substring(b+2));
							  
							  int c =day;
							  for(;day<=n;day++) {
								  d+=day+" ";
							  }
							  n=c;
						  }
						  //������ ���� ��
						  else {
							  day=Integer.parseInt(s2);
							  d+=day;
						  }
						  try {
								w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
							} catch (IOException e) {
								e.printStackTrace();
							}	
						 
					  }
					  }
					  
					  
				  }
				  	
		  try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
