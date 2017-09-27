package edu.wm.cs.cs301.amazebycarolinefaparnan.generation;
public class StubOrder implements Order {
	
	private int skill;
	private Builder builder ;
	private boolean perfect ;
	private MazeConfiguration mazeConfig ;

	
	public StubOrder(int skill, Builder builder, boolean perfect) {
		this.skill = skill;
		this.builder = builder ;
		this.perfect = perfect ;
		
		this.mazeConfig = new MazeContainer() ;
	}
		
	public int getSkillLevel() {
		return skill ;
	}
	/** 
	 * Gives the requested builder algorithm, possible values 
	 * are listed in the Builder enum type.
	 */
	public Builder getBuilder() {
		return builder ;
	}
	
	/**
	 * Describes if the ordered maze should be perfect, i.e. there are 
	 * no loops and no isolated areas, which also implies that 
	 * there are no rooms as rooms can imply loops
	 */
	public boolean isPerfect(){
		return perfect;
	};
	/**
	 * Delivers the produced maze. 
	 * This method is called by the factory to provide the 
	 * resulting maze as a MazeConfiguration.
	 * @param the maze
	 */
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeConfig  = mazeConfig ;
		
	}
	/**
	 * Provides an update on the progress being made on 
	 * the maze production. This method is called occasionally
	 * during production, there is no guarantee on particular values.
	 * Percentage will be delivered in monotonously increasing order,
	 * the last call is with a value of 100 after delivery of product.
	 * @param current percentage of job completion
	 */
	public void updateProgress(int percentage) {
	}
	
	public MazeConfiguration getConfiguration(){
		return mazeConfig ;
	}
}


