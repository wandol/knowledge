package egovframework.kf.common;

public enum RankingDomain {
	ALL("1"),
	BOILER("2"),
	GT_TURBINE("3"),
	ST_TURBINE("4"),
	GEN_PREV("5"),
	GEN_INS("6"),
	KEPRI("7"),
	PERFORM("8");
	
	final private String name;
     
    public String getName() {
        return name;
    }
 
    private RankingDomain(String name){
        this.name = name;
    }
}
