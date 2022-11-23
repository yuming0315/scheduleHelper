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
			  find=s.substring(start).indexOf("일: "); 
			  input=s.substring(start,start+find)+"@";
			  start+=find+3;
			  find=s.substring(start).indexOf("일: ");
			  if(find!=-1) {
				  end=start+find-1;
				  //end가 숫자가 아닐때까지 뒤로가기
				  while((s.charAt(end)>='0'&&s.charAt(end)<='9')||s.charAt(end)==' '
						  ||s.charAt(end)==','||s.charAt(end)=='~'||s.charAt(end)=='월'||s.charAt(end)=='일') 
				  {
					  if((s.charAt(end-1)>='0'&&s.charAt(end-1)<='9')&&((s.charAt(end)=='월'||s.charAt(end)=='일'))) {
						  end-=2;
					  }
					  else if((s.charAt(end)>='0'&&s.charAt(end)<='9')&&s.charAt(end-1)=='/') {
						  break;
					  }
					  else if(!(s.charAt(end-1)>='0'&&s.charAt(end-1)<='9')&&((s.charAt(end)=='월'||s.charAt(end)=='일'))) {
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
			  
			  
			//데이터 가공 및 파일에 저장
			  String [] data = input.split("@");
			  String daydata;
			  String d="";
				  //날짜가 하루일때
			  if(month==13) {
				  month=1;
			  }
				  int m=data[0].indexOf("월");
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
				  //"월" "일" 이 들어있을 때
				  else if(m!=-1) {
					  //월이 인덱스가 1이나 2일때는 이번달이 아님 //끝
					  //월의 인덱스가 1이나 2가 아닐때 이번달임 //시작
					  
					  if(m>2) {
						  data[1]+=" 시작";
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
						  data[1]+=" 끝";
						  int count=data[0].length()-1;
						  while(data[0].charAt(count)>='1'&&data[0].charAt(count)<='9')
							  count--;
						  day=Integer.parseInt(data[0].substring(count+1));
						  d+=day;
						  
						  if(day<n&&!data[1].equals("동계계절학기 끝"))
							  month++;
						  n=day;
						  
						  try {
								w.write(String.format("%d@%d@%s@%s\r",year,month,d,data[1]));
							} catch (IOException e) {
								e.printStackTrace();
							}	
						  
						  if(data[1].equals("하계계절학기 끝"))
							  month++;
					  }
				  }			  
				  //물결이나 쉼표가 있을때
				  else {
					  int a;
					  a=data[0].indexOf(",");
					  //쉼표가 없음
					  if(a==-1) {
						  //물결 검사
						  int b = data[0].indexOf("~");
						  //물결이 있음
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
					  //쉼표가 있음
					  else {
						  //쉼표 앞뒤로 나눠서 계산
						  String s1=data[0].substring(0,a);
						  int b = s1.indexOf("~");
						  //앞부분에 물결이 있는지 체크
						  //물결이 있을때
						  if(b!=-1) {
							  day=Integer.parseInt(s1.substring(0,b-1));
							  n=Integer.parseInt(s1.substring(b+2));
							  int c=day;
							  for(;day<=n;day++) {
								  d+=day+" ";
							  }
							  n=c;
						  }
						  //물결이 없을 때
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
						  //물결이 없을 때
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
