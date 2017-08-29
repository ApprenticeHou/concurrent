package concurrency;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * ��дһ�����������ĳ���
 */
public class FileCrawler{
	public static void main(String[] args) {
		File f=new File("C:\\Users\\DELL");
		File[] files=f.listFiles();
		BlockingQueue< File> bq=new LinkedBlockingQueue<File>();
		for(File file:files)
			new Thread(new clawltext(file, bq, new FileFilter() {
				public boolean accept(File pathname) {
					return true;
				}
			})).start();
		for(int i=0;i<3;i++)
			new Thread(new Indexer(bq)).start();
	}
}
class clawltext implements Runnable{
	private  final File root;
	private  final BlockingQueue<File> bq;
	private final FileFilter filefilter;
	public clawltext(File file ,BlockingQueue<File> bq,FileFilter ff){
		root=file;
		this.bq=bq;
		filefilter=ff;
	}
public void run(){
	try {
		crawl(root);
	} catch (InterruptedException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
}
/*
 * ����ָ�����ļ�
 */
public void crawl(File f) throws InterruptedException{
	File[] files=f.listFiles(filefilter);
	if(files!=null){
		for(File file:files)
			if(file.isDirectory()){
			crawl(file);
			}
			else{
				bq.put(file);
			
			}
	}		
}}
/*
 * ��дһ����������
 */
class Indexer implements Runnable{
	private final BlockingQueue<File> bq;
	public Indexer(BlockingQueue<File> bq){
		this.bq=bq;
	}
	public void run(){
		try {
		while(true)	{
			System.out.println("take");
			System.out.println(bq.take().getAbsolutePath());
		}
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
	}

	public void IndexFile(File f){
		System.out.println(f.getAbsolutePath());
	}
}
