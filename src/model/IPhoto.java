package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IPhoto extends Serializable{
	public String getPhotoID();
	public void setPhotoID(String id);
	
	public String getFileName();
	public void setFileName(String fileName);
	public String getCaption();
	public void setCaption(String caption);
	public Date getDate();
	/**
	 * @param date A Date.util object whose milliseconds have been set to zero e.g. cal.set(Calendar.MILLISECOND,0);
	 */
	public void setDate(Date date);
	
	/*
	 * Tags
	 */
	public <V> V getTagInjective(String tagType);
	public <V> void setTagInjective(String tagType, V value);
	public <V> List<V> getTagSurjective(String tagType);
	public <V> void setTagSurjective(List<V> value);
	
	/*
	 * Attributes
	 */
	public void setAttribute(String attributeId, String setting);
	
}
