package entity;

public class MethodEntity {
    private String methodName;
    private int totalLines;
    private boolean hasAnnotation;

   public MethodEntity(String methodName){
       this.methodName = methodName;
   }

   public boolean isHasTestInName(){
       return methodName.startsWith("test");
   }

    public boolean isHasAnnotation() {
        return hasAnnotation;
    }

    public void setHasAnnotation(boolean hasAnnotation) {
        this.hasAnnotation = hasAnnotation;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }
}
