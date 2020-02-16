package net.improvedsurvival.interfaces;

public interface IEnvironmentalEntity {
    public void addTemperature(float amount);
    public void setTemperature(float amount);
    public float getTemperature();
    public void addWetness(float amount);
    public void setWetness(float amount);
    public float getWetness();
}