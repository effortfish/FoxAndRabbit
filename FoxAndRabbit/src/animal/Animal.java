package animal;

import field.Location;

public abstract class Animal
{
    private int ageLimit;
    private int breedableAge;
    private int age;
    private boolean isAlive = true;

    public Animal(int ageLimit,int breedableAge)
    {
        this.ageLimit = ageLimit;
        this.breedableAge = breedableAge;
    }

    public int getAge()
    {
        return age;
    }

    public void grow()
    {
        age++;
        if(age >= ageLimit)
            die();
    }

    public void die()
    {
        isAlive = false;
    }

    public double getAgePercent()
    {
        return (double)age/ageLimit;
    }

    public void longerlife(int addage)
    {
        ageLimit = ageLimit + addage;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public abstract Animal breed();

    public String toString(){
        return " "+age+":"+(isAlive?"live":"dead");
    }

    public boolean isBreedable()
    {
        return age > breedableAge;
    }

    public Location move(Location[] freeAdj){
        Location ret = null;
        if( freeAdj.length > 0 && Math.random() < 0.02 ){
            ret = freeAdj[(int)(Math.random()*freeAdj.length)];
        }
        return ret;
    }


}
