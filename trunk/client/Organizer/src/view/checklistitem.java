package view;

public class checklistitem
{
private String  label;
private boolean isSelected = false;
 
public checklistitem(String label)
{
this.label = label;
}
 
public boolean isSelected()
{
return isSelected;
}
 
public void setSelected(boolean isSelected)
{
this.isSelected = isSelected;
}
 
public String toString()
{
return label;
}
}