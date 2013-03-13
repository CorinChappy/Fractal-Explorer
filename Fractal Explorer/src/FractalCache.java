


class FractalCache {
	private int[][] cache;
	private int width;
	private int height;
	
	public FractalCache(){}
	
	public void remake(int width, int height){
		cache = new int[width][height];
		this.width = width;
		this.height = height;
	}
	
	public int[][] dumpCache(){
		return cache;
	}
	
	public void overWrite(int[][] a){
		cache = a;
	}
	
	public void clearCache(){
		cache = null;
	}
	
	public boolean isNull(){
		return (cache == null);
	}
	
	
	public int add(int it, int x, int y) throws CacheFullException{
		try{
			cache[x][y] = it;
		}catch(ArrayIndexOutOfBoundsException e){
			throw new CacheFullException();
		}
		
		return it;
	}
	
	public int get(int x, int y) throws CacheFullException{
		try{
			return cache[x][y];
		}catch(ArrayIndexOutOfBoundsException e){
			throw new CacheFullException();
		}
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	
}

@SuppressWarnings("serial")
class CacheFullException extends Exception{
	public CacheFullException(){
		super();
	}
	public CacheFullException(String m) {
		super(m);
	}
}
