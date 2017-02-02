package by.gomelagro.outcoming.properties;

/**
 * 
 * @author mcfloonyloo
 * @version 0.1
 *
 */

public class ApplicationPropertiesTemp {

	private String libraryPath; 		//путь к файлам dll папки Avest Java Provider
	private String classPath;   		//путь к файлам .class проекта
	private String filePath;			//путь к файлу выгрузки списка
	private String dbPath;				//путь к базе данных
	private String folderInvoicePath;	//путь к папке Ё—„‘
	
	private String urlService;			//сетевой путь к сервису Ё—„‘
	
	private boolean loadfileMenuitem;	//отображение пункта меню «агрузить файл
	
	public String getLibraryPath(){return this.libraryPath;}
	public String getClassPath(){return this.classPath;}
	
	public String getFilePath(){return this.filePath;}
	public String getDbPath(){return this.dbPath;}
	public String getFolderInvoicePath(){return this.folderInvoicePath;}
	
	public String getUrlService(){return this.urlService;}
	
	public boolean getLoadfileMenuitem(){return this.loadfileMenuitem;}
	
	private ApplicationPropertiesTemp(Builder build){
		this.libraryPath = build.libraryPath;
		this.classPath = build.classPath;
		this.filePath = build.filePath;
		this.dbPath = build.dbPath;
		this.folderInvoicePath = build.folderInvoicePath;
		
		this.urlService = build.urlService;
		
		this.loadfileMenuitem = build.loadfileMenuitem;
	}
	
	public final static class Builder{
		private final static Builder instance = new Builder();
		
		private String libraryPath;
		private String classPath;
		private String filePath;
		private String dbPath;
		private String folderInvoicePath;
		
		private String urlService;
		
		private boolean loadfileMenuitem;
		
		public static Builder getInstance(){
			return instance;
		}
		
		private Builder(){/*Singleton*/}
		
		public Builder setLibraryPath(String libraryPath){
			this.libraryPath = libraryPath;
			return this;
		}
		
		public Builder setClassPath(String classPath){
			this.classPath = classPath;
			return this;
		}
		
		public Builder setFilePath(String filePath){
			this.filePath = filePath;
			return this;
		}
		
		public Builder setDbPath(String dbPath){
			this.dbPath = dbPath;
			return this;
		}
		
		public Builder setFolderInvoicePath(String folderInvoicePath){
			this.folderInvoicePath = folderInvoicePath;
			return this;
		}
		
		public Builder setUrlService(String urlService){
			this.urlService = urlService;
			return this;
		}
		
		public Builder setLoadfileMenuitem(boolean loadfileMenuitem){
			this.loadfileMenuitem = loadfileMenuitem;
			return this;
		}
		
		public ApplicationPropertiesTemp build(){
			return new ApplicationPropertiesTemp(this);
		}
	}
}
