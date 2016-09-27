package testconsoleview.parts;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ConsoleView  {
 private Text text;
 StringBuffer str;
 int cnt = 0;
 byte[] buf ={0,0};
 @PostConstruct
 public void createPartControl(Composite parent) {
  str = new StringBuffer(2);
  text = new Text(parent, SWT.READ_ONLY | SWT.MULTI |SWT.V_SCROLL | SWT.H_SCROLL);
 
  OutputStream out = new OutputStream() {
   @Override
   public void write(int b) throws IOException {
    if (text.isDisposed()) return;
    	if(b>=0&&b<=127)
    		text.append(String.valueOf((char) b));
    	else
    	{
    		buf[cnt] =  (byte)b;
    		cnt ++;
    		if(cnt == 2)
    		{
    			String str = new String (buf);
    			cnt = 0;
    			text.append(str);
    			
    		}
    		
        	
    	}  	
   }
  }; 
  
  final PrintStream oldOut = System.out;
  System.setOut(new PrintStream(out));
  text.addDisposeListener(new DisposeListener() {
   public void widgetDisposed(DisposeEvent e) {
    System.setOut(oldOut);
   }
  });
 }

 public void setFocus() {
  text.setFocus();
 }
}