import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

class scheduleInput{
   String lesson,classRoom;
   String days;
   
   scheduleInput(String lesson, String classRoom,String dayData){
      this.lesson=lesson;
      this.classRoom=classRoom;
      this.days=dayData;
   }
   
   public String toString() {
      return lesson+"Token"+classRoom+"Token"+days;
   }
   
}

//시간표 클래스
class Schedule extends JFrame{

   
   JPanel p1 = new JPanel();
   String day[] = {"Mon","Tue","Wed","Thu","Fri"};
   ImageIcon bono;
   JButton Sdata[][]=new JButton[9][6];
   
   JPanel p2 = new JPanel();
   JPanel p4 = new JPanel();
   JButton addData = new JButton();
   JButton removeData = new JButton();
   JButton removeAll=new JButton();
   JLabel nowSelect = new JLabel("");
   
   String ID;
   String path;
   String path2;

      
   void bonobono() {
      ImageIcon i = new ImageIcon("images/보노시간.png");
      Image originImg = i.getImage(); 
      Image changedImg= originImg.getScaledInstance(270, 192, Image.SCALE_SMOOTH );
      bono = new ImageIcon(changedImg);
      int w=60,h=60;
      
      i=new ImageIcon("images/추가.png");
      originImg = i.getImage();   
      changedImg=originImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
      i=new ImageIcon(changedImg);
      addData.setIcon(i);
      addData.setFocusPainted(false);
      addData.setBorderPainted(false);
      addData.setBackground(new Color(255,255,224));
      
      i=new ImageIcon("images/삭제.png");
      originImg = i.getImage();   
      changedImg=originImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
      i=new ImageIcon(changedImg);
      removeData.setIcon(i);
      removeData.setFocusPainted(false);
      removeData.setBorderPainted(false);
      removeData.setBackground(new Color(255,255,224));
      
      i=new ImageIcon("images/전체삭제.png");
      originImg = i.getImage();   
      changedImg=originImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
      i=new ImageIcon(changedImg);
      removeAll.setIcon(i);
      removeAll.setFocusPainted(false);
      removeAll.setBorderPainted(false);
      removeAll.setBackground(new Color(255,255,224));      
   }
    
   public Schedule(JFrame frame,String s) {
      super("시간표");
      this.ID=s;
      path=ID+File.separator+"weekEnd.txt";
      path2=ID+File.separator+"change.txt";
      getContentPane().setBackground(new Color(255,250,205));
      setLayout(null);
      setButton();
      setScheduleAction();
      
      makeSchedule();
      makeMenu();
      
      p4.setLayout(null);
      p4.setSize(500,500);
       p4.setLocation(30,30);
       p4.setBackground(new Color(255,255,224));
       p4.add(p1);
       
       add(p4);
       
       bonobono();
       JLabel BONO=new JLabel(bono);
      BONO.setBounds(535, 330,270,192);
      add(BONO);
       
      setSize(820,600);
      setVisible(true);         
   }
   public static void Main(String[] args) {
      Schedule s=new Schedule(null,"c");
   }
   void makeSchedule() {
       //파일처리로 저장된 데이터 불러오기.
       try {
          File f = new File(path);
          if(f.createNewFile()) ;
          //파일이 이미 있을때 불러오기. 스트링 넘겨줘서 받아오기
          else {
             weekEndOpen(path);
          }
       }
       catch(IOException e){
          System.out.println("오류가 발생했습니다.");
          e.printStackTrace();
       }
   }
   
   //파일에서 한줄단위로 받아와서 토큰 분리하고 요일데이터 한번 더 공백으로 분리해서 맞는칸에 데이터 넘기기
   void weekEndOpen(String s) {
      try{
         FileReader f = new FileReader(s);
         BufferedReader f1=new BufferedReader(f);
         String str="",ddata;
         while((str=f1.readLine())!=null) {
            String[] data = str.split("Token");
            String d = data[0]+" "+data[1];
            String[] dday=data[2].split(" ");
            for(int k=0;k<dday.length;k++) {
               for(int i=1;i<Sdata.length;i++) {
                     for(int j=1;j<Sdata[i].length;j++) {
                        ddata = String.format("%s%d",Sdata[0][j].getText(),i);
                        if(dday[k].equals(ddata)) {
                           Sdata[i][j].setText(d);
                        }
                     }
                  }
            }
         }
         f1.close();
         f.close();
      }
      catch(IOException e){
          System.out.println("오류가 발생했습니다.");
          e.printStackTrace();
       }
   }
   
   //파일에 데이터 한 줄 쓰기
   void weekEndWrite(String s) {
      try {
         FileWriter f = new FileWriter(path,true);
         BufferedWriter f1 = new BufferedWriter(f);
         f1.write(s);
         //f1.newLine();
         f.close();
      }
      catch(IOException e) {
         System.out.println("입출력 오류");
      }
   }
   
   
   void setButton() {
      for(int i=0;i<Sdata.length;i++) {
          for(int j=0;j<Sdata[i].length;j++) {
             Sdata[i][j]=new JButton();
             Sdata[i][j].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   JButton b = (JButton)e.getSource();
                   if(b.getText().equals("SELECT")) {
                        b.setText("");
                        //라벨에서 검색해서 데이터 빼야함.
                        String s="",l="";
                        //nowselect의 값에서 제거
                        int n;
                        for(int i=1;i<Sdata.length;i++) {
                         for(int j=1;j<Sdata[i].length;j++) {
                            if(b==Sdata[i][j]) {
                               s = String.format("%s%d ",Sdata[0][j].getText(),i);
                               break;
                            }
                         }
                      }
                        n=nowSelect.getText().indexOf(s);
                        if(n==0) {
                           l+=nowSelect.getText().substring(n+s.length()-1);
                        }
                        else {
                        l=nowSelect.getText().substring(0, n-1);
                         l+=nowSelect.getText().substring(n+s.length()-1);
                        }
                        nowSelect.setText(l);
                        //만약 select한 버튼에 데이터가 있었을 경우 다시 불러오기
                        try{
                           FileReader f = new FileReader(path);
                           BufferedReader f1=new BufferedReader(f);
                           String str="",ddata;
                           while((str=f1.readLine())!=null) {
                              String[] data = str.split("Token");
                              String d = data[0]+" "+data[1];
                              String[] dday=data[2].split(" ");
                              for(int k=0;k<dday.length;k++) {
                                 for(int i=1;i<Sdata.length;i++) {
                                       for(int j=1;j<Sdata[i].length;j++) {
                                          ddata = String.format("%s%d",Sdata[0][j].getText(),i);
                                          if(dday[k].equals(s.substring(0, 4))&&dday[k].equals(ddata)) {
                                             Sdata[i][j].setText(d);
                                          }
                                       }
                                    }
                              }
                           }
                           f1.close();
                           f.close();
                        }
                        catch(IOException e123){
                            System.out.println("오류가 발생했습니다.");
                            e123.printStackTrace();
                         }

                  }
                  else {
                     if(!b.getText().equals("SELECT")) {
                        for(int i=1;i<Sdata.length;i++) {
                            for(int j=1;j<Sdata[i].length;j++) {
                               if(b==Sdata[i][j])
                                 b.setText("SELECT");
                            }
                        }
                     }
                      for(int i=1;i<Sdata.length;i++) {
                         for(int j=1;j<Sdata[i].length;j++) {
                            if(b==Sdata[i][j]) {
                               String s = nowSelect.getText();
                               s+=String.format("%s%d ", day[j-1],i);
                               nowSelect.setText(s);
                            }
                         }
                      }
                  }
               }
               });
            }
         }
      for(int i=0;i<Sdata.length;i++) {
          for(int j=0;j<Sdata[i].length;j++) {
             Sdata[i][j].setOpaque(true);
             if(i==0||j==0)Sdata[i][j].setBackground(new Color(0xffddae));
             else {
                Sdata[i][j].setBackground(new Color(255,255,255));
             }
          }
       }
      p1.setSize(480,480);
       p1.setLocation(10,10);
       p1.setBackground(new Color(255,255,224));
      p1.setLayout(new GridLayout(0,6));
      p1.add(Sdata[0][0]);
      for(int i=1;i<day.length+1;i++ ) {
         Sdata[0][i].setText(day[i-1]);
         p1.add(Sdata[0][i]);
      }
      for(int i=1;i<Sdata.length;i++) {
         Sdata[i][0].setText(Integer.toString(i));
         p1.add(Sdata[i][0]);
         for(int j=1;j<Sdata[i].length;j++) {
            Sdata[i][j].setText("");
            Sdata[i][j].setContentAreaFilled(false);
            Sdata[i][j].setFocusPainted(false);
            p1.add(Sdata[i][j]);
         }
      }
   }
   
   void setScheduleAction() {
      addData.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(!nowSelect.getText().equals("")) {
               new addScheduleData(nowSelect.getText().substring(0,nowSelect.getText().length()-1));
               nowSelect.setText("");
               weekEndOpen(path);
            }
            else
               JOptionPane.showMessageDialog(null, "추가할 시간을 선택해 주세요");
               
         }
      });
      removeData.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               FileReader f = new FileReader(path);
               BufferedReader f1=new BufferedReader(f);
               FileWriter fi = new FileWriter(path2);
               BufferedWriter fi1 = new BufferedWriter(fi);
               String str="",ddata;
               while((str=f1.readLine())!=null) {
                  String[] data = str.split("Token");
                  String d = data[0]+" "+data[1];
                  String[] dday=data[2].split(" ");
                  for(int k=0;k<dday.length;k++) {
                     for(int i=1;i<Sdata.length;i++) {
                           for(int j=1;j<Sdata[i].length;j++) {
                              if(Sdata[i][j].getText().equals("SELECT")) {
                                 ddata = String.format("%s%d",Sdata[0][j].getText(),i);
                                 if(dday[k].equals(ddata)) {
                                    dday[k]="";
                                    Sdata[i][j].setText("");
                                 }
                              }      
                           }
                        }
                  }
                  
                  int n=0;
                  String daydata="";
                  for(int i=0;i<dday.length;i++) {
                     if(!dday[i].equals(""))
                     daydata+=dday[i]+" ";
                     else
                        n++;
                  }
                  if(n==dday.length)
                     continue;
                  fi1.write(data[0]+"Token"+data[1]+"Token"+daydata);
                  fi1.newLine();
               }
               fi1.close();
               f1.close();
               File oldFile = new File(path);
               oldFile.delete();
               File newFile=new File(path2);
               newFile.renameTo(oldFile);   
               weekEndOpen(path);   
            }
            catch(Exception e12){
                System.out.println("오류가 발생했습니다.");
                e12.printStackTrace();
             }
            nowSelect.setText("");
         }
      });
      removeAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                  File file = new File(path); 
                  if( file.exists() ){ 
                     if(file.delete()){ System.out.println("파일삭제 성공"); }
                     else{ System.out.println("파일삭제 실패"); } }
                  else{ System.out.println("파일이 존재하지 않습니다."); } 
               } catch (Exception e1) {
                  e1.printStackTrace();
               }
               makeSchedule();
               for(int i=1;i<Sdata.length;i++) {
                  for(int j=1;j<Sdata[i].length;j++) {
                     Sdata[i][j].setText("");
                  }
               }
               nowSelect.setText("");
               weekEndOpen(path);
            }
         });
   }
   
   void makeMenu(){
      p2.setLayout(null);
      JLabel j = new JLabel("NowSelect");
      j.setBounds(20, 0, 300, 35);
      p2.add(j);
      nowSelect.setBounds(20,25,300,35);
      p2.add(nowSelect);
      addData.setBounds(30,80,60,60);
      p2.add(addData);
      removeData.setBounds(110,80,60,60);
      p2.add(removeData);
      removeAll.setBounds(110,150,60,60);      
      p2.add(removeAll);
      p2.setBounds(570,30,200,220);
      p2.setBackground(new Color(255,255,224));
      add(p2);
   }
   
   //확인 눌렀을 때
   class addScheduleData extends JFrame{
      public boolean count;
      JPanel p3 = new JPanel();
      JButton 확인 = new JButton("");
      JButton 취소 = new JButton("");
      JLabel nowSelect = new JLabel();
      JLabel select = new JLabel("SELECT");
      JLabel lesson = new JLabel("수업이름");
      JLabel classRoom = new JLabel("강의실");
      JTextField lt = new JTextField(30);
      JTextField ct = new JTextField(30);
      
      addScheduleData(String s){
         this.setLayout(null);
         count=false;
         p3.setLayout(null);
         
         ImageIcon i=new ImageIcon("images/확인.png");
         Image originImg = i.getImage();   
         Image changedImg=originImg.getScaledInstance(100, 75, Image.SCALE_SMOOTH);
         i=new ImageIcon(changedImg);
         확인.setIcon(i);
         확인.setFocusPainted(false);
         확인.setBorderPainted(false);
         확인.setBackground(new Color(255,255,224));
         
         확인.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //라벨 넘겨주기 및 창닫기
               count=true;
               isCount();
               weekEndOpen(path);
               dispose();
            }
         });
         
         i=new ImageIcon("images/취소.png");
         originImg=i.getImage();
         changedImg=originImg.getScaledInstance(100, 75, Image.SCALE_SMOOTH);
         i=new ImageIcon(changedImg);
         취소.setIcon(i);
         취소.setFocusPainted(false);
         취소.setBorderPainted(false);
         취소.setBackground(new Color(255,255,224));
         
         취소.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //라벨 초기화 및 창닫기
               count=false;
               isCount();
               dispose();
            }
         });
         확인.setBounds(45,255,100,75);
         취소.setBounds(205,255,100,75);

         p3.add(확인);
         p3.add(취소);
         
         nowSelect.setText(s);
         nowSelect.setBounds(25,50,200,35);
         p3.add(nowSelect);
         select.setBounds(25,0,100,100);
         p3.add(select);
               
         lesson.setBounds(25,100,200,35);
         lt.setBounds(25,125,300,35);
         classRoom.setBounds(25,175,300,35);
         ct.setBounds(25,200,300,35);
         
         p3.add(lesson);
         p3.add(lt);
         p3.add(classRoom);
         p3.add(ct);
         p3.setBackground(new Color(255,255,224));
         p3.setBounds(20, 20, 355, 350);
         
         add(p3);
         getContentPane().setBackground(new Color(255,250,205));
         setSize(415,430);
         setVisible(true);
      }
      void weekEndWrite() {
         scheduleInput s = new scheduleInput(lt.getText(),ct.getText(),nowSelect.getText());
         try {
            FileWriter f = new FileWriter(path,true);
            BufferedWriter bw = new BufferedWriter(f);
            bw.write(s.toString());
            bw.newLine();
            
            bw.close();
            
            weekEndOpen(path);
         }
         catch(IOException e) {
            System.out.println("입출력 오류");
         }
      }
      void isCount() {
         if(count==true) {
            weekEndWrite();
         }
      }
   }
}