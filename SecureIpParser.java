import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class SecureIpParser implements Runnable{
	private Thread myThread;
	private String text;
	
	private Set<String> blacklist = new HashSet<String>();
	private Set<String> whitelist = new HashSet<String>();
	public SecureIpParser(String _text){
		this.text=_text;
		myThread=new Thread(this);
		myThread.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader sReader=new BufferedReader(new StringReader(text));
		String buffer;
		String ip;
		int fromIndex=0;
		int portIndex=0;
		try {
			while((buffer=sReader.readLine())!=null){
				if(buffer.indexOf("Failed password")>=0){
					// Failed users
					fromIndex = buffer.indexOf("from")+4;
					portIndex = buffer.indexOf("port",fromIndex);
					ip=buffer.substring(fromIndex, portIndex).trim();
					blacklist.add(ip);
					
				}else if(buffer.indexOf("Accepted password")>=0){
					// Accept user
					fromIndex = buffer.indexOf("from")+4;
					portIndex = buffer.indexOf("port",fromIndex);
					
					ip=buffer.substring(fromIndex, portIndex).trim();
					whitelist.add(ip);
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getBlacklist(){
		String[] ret=null;
		try {
			myThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ret;
		}
		ret=new String[blacklist.size()];
		blacklist.toArray(ret);
		return ret;
	}
	
	public String[] getWhitelist(){
		String[] ret=null;
		try {
			myThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ret;
		}
		ret=new String[whitelist.size()];
		whitelist.toArray(ret);
		return ret;
	}
	
}
