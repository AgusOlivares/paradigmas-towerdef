package org.example.Enemigos;


import org.example.Map.Map;
/**
 * especialización de Enemy para el modelado de enemigo de tipo enano
 * @author Luciana Puentes
 */
public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;
    /**
     * Crea un enemigo de tipo enano 
     * @param map el maoa donde se crea el enemigo de tipo enano
     */
    public EnemyDwarf(Map map) {
        /**
         * @see Enemy
         */
        super(map, 100, 66, 10, 1);
    }
    /**
     * método que aumenta el daño que inflinge el enano
     * si se encuentra a más de la mitad de la vida
     */
    public void gigaDwarf() {
        /**
         * si la vida actual del enano es maypr a la mitad de su vida base,
         * establece su daño como el doble del daño base
         */
        if (health > (int)baseHealth / 2) {
            damage = baseDamage * 2;}
        else{
            damage = baseDamage;
        }


        }
}
