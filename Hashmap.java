import java.util.Locale;

public class Hashmap<K ,V extends Number > {
    int capacity=997;
    private int size;
    int collision_count;
    private Entry[] table = new Entry[capacity];


    public int hashcode(K key) {

        //PAF
        /*
        int hash1 =0;
        String str= (String)key;
        str=str.toLowerCase(Locale.ENGLISH);
        for(int i=0;i<str.length();i++){
            hash1 += Math.pow(31, str.length()-i-1)*(str.charAt(i)-96);
            hash1=Math.abs(hash1);
        }
        return hash1;


         */



        //MY HASH FUNCTION
        String str= (String)key;
        str=str.toLowerCase(Locale.ENGLISH);
        int hash1 = 0;
        int x ;
        for(int i = 0; i < str.length(); i++)
        {
            hash1 = (hash1 << 4) + str.charAt(i);
            if((x = (int) (hash1 & 0xF0000000L)) != 0)
            {
                hash1 ^= (x >> 24);
            }
            hash1 &= ~x;
        }
        return hash1;



    }




    public int hashFunction(K key) {
        int hash = (hashcode(key) % capacity);
        return hash;
    }

    public V get(K key) {

        V returnn = null;

        int location = hashFunction(key);

        if (table[location] == null){
            return null;
        }

        //Key var ise :
        if (table[location] != null && table[location].getKey().equals(key))
        {
            return (V) table[location].getVal();
        }

        //Kelimeyi eklemiş miyim diye ileriye bakıyorum.
        if (table[location] != null && !table[location].getKey().equals(key))
        {
            while (table[location] != null && !table[location].getKey().equals(key)) {
                location = (location + 1) ; //% capacity;
                //Eklemişsem value değerini döndürüyorum:
                if (table[location] != null && table[location].getKey().equals(key)) {
                    return (V) table[location].getVal();
                }
            }
        }
        return returnn;
    }



    public V put(K key, V value) {
        V returnn = null;
        int location = hashFunction(key);
        int oldLocation=location;

        //int dib_old=location-hashFunction((K) table[location].getKey());
        int dib_current=location-hashFunction(key);


        if(((size*1.0)/(capacity))>=0.50){
            resize();
        }
        //hashFunction(key2)
        //Aynı kelime eklenirse:  Value artacak, kelime eklenmeyecek.
        if (table[location] != null && table[location].getKey().equals(key))
        {
            //System.out.println("Hash:"+" "+key+" "+location); //KONTROL AMAÇLI
            table[location].setVal(value);
        }


        //Eğer gelen key'in location'ının olması gereken location'a uzaklığı, key'in yerleştirileceği yerdeki kelimenin location'ının olması gerektiği location'a olan uzaklığından büyükse, ikisini swap etmem gerek.
        //Farklı iki kelimenin location'ları aynı çıkarsa, hashmapi gez.(Kelimenin aynısı belki de daha ileride zaten girilmiştir.)
        if (table[location] != null && !table[location].getKey().equals(key)){


            while( (table[location] != null && !table[location].getKey().equals(key) ) ){
                location = (location + 1); //% capacity;


                //Kelimeyi bulamadık ama bulmadan önce DIB değeri daha düşük olan bir kelime buldum, yerlerini değiştirmem gerek.
                if(table[location] != null  && !table[location].getKey().equals(key) ) {


                    int dib_old=location-hashFunction((K) table[location].getKey());
                    dib_current=location-hashFunction(key);
                    if(dib_current>dib_old){

                        //Yerini değiştireceğim key'in key ve value'sunu alıyorum.
                        Entry<K, V> eOld = new Entry<>((K) table[location].getKey(), (V) table[location].getVal());

                        //Yeni keyi ekliyorum.
                        Entry<K, V> eNew = new Entry<>(key, value);
                        eNew.setKey(key);
                        eNew.setVal(value);
                        table[location] = eNew;

                        //Eski kelimeyi yeniden put ediyorum.
                        //int location2=location;
                        put(eOld.getKey(),eOld.getVal());
                        if(((size*1.0)/(capacity))>=0.50){
                            resize();
                        }
                        collision_count++;
                        break;
                    }
                }

                //KELİMEYİ BULDUK, VALUE'SUNU ARTTIR.
                if(table[location] != null && table[location].getKey().equals(key)){
                    //System.out.println("Hash:"+" "+key+" "+location); //KONTROL AMAÇLI
                    table[location].setVal(value);
                    return returnn;
                }
                if(table[oldLocation] != null && table[oldLocation].getKey().equals(key)){
                    //System.out.println("Hash:"+" "+key+" "+location); //KONTROL AMAÇLI
                    table[oldLocation].setVal(value);
                    return returnn;
                }
            }
            //Kelimenin aynısı girilmemiş.
            if(table[location]==null){
                Entry<K, V> eNew = new Entry<>(key, value);
                eNew.setKey(key);
                eNew.setVal(value);
                table[location] = eNew;
                size++;
                //System.out.println("Hash:"+" "+key+" "+location); //KONTROL AMAÇLI
                if(((size*1.0)/(capacity))>=0.50){
                    resize();
                }
            }

        }
        else if (table[location]==null) {

            Entry<K, V> eNew = new Entry<>(key, value);
            eNew.setKey(key);
            eNew.setVal(value);
            table[location] = eNew;
            size++;
            //System.out.println("Hash:"+" "+key+" "+location); //KONTROL AMAÇLI
            if(((size*1.0)/(capacity))>=0.50){
                resize();
            }
        }
        return returnn;
    }
    public boolean containsKey(K key) {
        int location = hashFunction(key);
        if(location >= capacity) {
            location=location%capacity;
        }
        if (table[location] != null && table[location].getKey().equals(key))
        {
           return true;
        }
        if (table[location] != null && !table[location].getKey().equals(key)){
            while(table[location] != null && !table[location].getKey().equals(key) ){
                location = (location + 1); //% capacity;
                if(table[location]!=null){
                    if(table[location].getKey().equals(key)){
                        return true;
                    }
                }

            }

        }

        return false;
    }


    public void resize(){
        capacity*=2;
        Entry[] tableTmp = new Entry[capacity];
        Entry[] oldTable = table;
        table=tableTmp;
        size=0;


        for (int i =0;i<oldTable.length;i++){
            if(oldTable[i]!=null){
                K key= (K) oldTable[i].getKey();
                V value= (V) oldTable[i].getVal();
                put(key,value);
            }
        }
    }

    public void write() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Key: "+table[i].getKey() + " " +"Index: "+i+" " +" "+" "+"Value: "+table[i].getVal());
            }
        }
        System.out.println("Number of Different Words:"+ ""+ size);
        //System.out.println("Hashmap Capacity:"+ ""+ capacity);
    }


}
