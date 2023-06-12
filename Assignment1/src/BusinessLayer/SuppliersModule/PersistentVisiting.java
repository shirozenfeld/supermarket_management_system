package BusinessLayer.SuppliersModule;

import java.util.*;

public class PersistentVisiting  extends Supplier {
    List<Day> days;

    public PersistentVisiting(String name, List<String> domains, Card card, Contract contract, List<Day> days) {
        super(name, domains, card, contract);
        this.domains = domains;
        this.days = days;
    }

    public PersistentVisiting(String name, List<String> domains, Card card, Contract contract, List<Day> days,String supplier_id,int catalogNumber)
    {
        super(supplier_id,name, domains, card, contract,catalogNumber);
        this.domains = domains;
        this.supplier_id = supplier_id;
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        this.days.add(day);
    }

    public void removeDay(Day day) {
        this.days.remove(day);
    }

    public int get_minimial_supplying_time() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        int int_day, distance = 0;
        Arrays.sort(this.days.toArray());
        for (int i = 0; i < days.size(); i++) {
            int_day = (days.get(i)).ordinal() + 1;
            if (today <= int_day)
                distance = int_day - today;
            if (days.get(days.size() - 1).ordinal() + 1 < today)
                distance = 7 - today + days.get(0).ordinal() + 1;
        }
        return distance;
    }
}
