package gamemodel.combatengine;

import gamecontrol.GlobalVariables;
import gamecontrol.contents.Enemy;
import gamecontrol.contents.CrewMember;
import gamemodel.mapengine.MainMap;
import java.io.IOException;
import ui.UICommandHelper;
import ui.inventory.UIInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import ui.maps.UIEnterSubarea;
import ui.maps.UIMainMap;

import static gamecontrol.GlobalVariables.*;
import static gamemodel.combatengine.UICombat.*;

public class EngageEnemy {
    private static final Random rg = new Random();

    public static ArrayList<CrewMember> KIAList = new ArrayList<>();
    public static ArrayList<Enemy> enemyKIAList = new ArrayList<>();

    static boolean retreat = false;

    private static int rounds = 1;

    public static void gameEnginePrototype() throws IOException, InterruptedException {
        rounds = 1;

        while (enemySquad.size() > 0 && mySquad.get(0).getHP() > 0) {
            reportCombatRounds(rounds);

            int mySquadInitiative = rg.nextInt(3);

           reportEngage();

            retreat = false;

            reportInitiativeStatus(mySquadInitiative);

            if (mySquadInitiative > 0) {
                playerMove();
                if (retreat) {
                    break;
                }
                restOfMySquadMove();

                enemySquadMove();
            } else {
                enemySquadMove();

                playerMove();
                if (retreat) {
                    break;
                }
                restOfMySquadMove();
            }

            reportCombatResult(mySquadInitiative);
            rounds += 1;
        }
    }

    private static Integer playerCommandParser() {
        Integer behaviour = null;

        while (behaviour == null) {
            Scanner s1 = new Scanner(System.in);
            var input = s1.nextLine();

            behaviour = inGameCommands.get(input);

            if (behaviour == null) {
                System.out.println("Invalid Command");
            }
        }

        return behaviour;
    }

    private static void playerMove() {
        reportPlayerMove();

        Integer command = playerCommandParser();

        switch (command) {
            case 26:
                playerAttackEnemy();
                break;
            case 27:
                playerUseItems();
                break;
//            case 28:
//                playerPlayTricks();
//                break;
            case 29:
                playerAutoCombat(-1);
                break;
            case 30:
                playerRetreat();
                break;
            case 17:
                UICommandHelper.showHelp();
                break;
            case 20:
                UICommandHelper.showHelpCombat();
                break;
            default:
        }
    }

    private static void playerAttackEnemy() {
        System.out.println("-----choose a target-----");

        HashMap<String, Integer> targets = new HashMap<>();
        for (int i = 0; i < enemySquad.size(); i++) {
            targets.put(String.valueOf(i + 1), i);
        }

        Integer target = null;
        while (target == null) {
            Scanner s1 = new Scanner(System.in);
            var selection = s1.nextLine();

            target = targets.get(selection);
            if (target == null) {
                System.out.println("-----Invalid Selection-----");
            }
        }

        playerAutoCombat(target);
    }

    private static void playerUseItems() {

        UIInventory.displayInventoryList();
    }

    private static void playerPlayTricks() {

    }

    private static void playerRetreat() {
        retreat = rg.nextBoolean();
        reportRetreatResults(retreat);
    }

    private static void playerAutoCombat(Integer target) {
        if(target < 0) {
            target = rg.nextInt(enemySquad.size());
        }

        Enemy enemy = enemySquad.get(target);
        var player = mySquad.get(0);
        var dmg = player.getAttack() + player.getWeapon().getWeapon_base_dmg();

        enemy.setHP(enemy.getHP() - dmg);
        if (enemy.getHP() <= 0) {
            enemySquad.remove(enemy);
            enemyKIAList.add(enemy);
        }
    }

    private static void restOfMySquadMove() {
        for (int i = 1; i < mySquad.size(); i++) {
            if (enemySquad.size() == 0) {
                break;
            }

            int target = rg.nextInt(enemySquad.size());
            int dmg = mySquad.get(i).getAttack() + mySquad.get(i).getWeapon().getWeapon_base_dmg();

            Enemy enemy = enemySquad.get(target);
            enemy.setHP(enemy.getHP() - dmg);

            if (enemy.getHP() <= 0) {
                enemySquad.remove(enemy);
                enemyKIAList.add(enemy);
            }
        }
    }

    private static void enemySquadMove() throws IOException, InterruptedException {
        for (Enemy en : enemySquad) {
            if (mySquad.get(0).getHP() <= 0) {
                UIEnterSubarea.setExit(true);
                UIMainMap.displayMainMapUI();
                break;
            }

            int target = rg.nextInt(mySquad.size());
            int dmg = en.getAttack();

            CrewMember crew = mySquad.get(target);
            crew.setHP(crew.getHP() - dmg);

            if (crew.getHP() <= 0) {
                KIAList.add(crew);
                if (target != 0) {
                    mySquad.remove(crew);
                }
            }
        }

        //enemy summon minions
        if(enemySquad.get(0).getName().equals(FINAL_BOSS) && rounds%5 == 0 && enemySquad.size()<5) {
            Enemy ens = MainMap.newEnemy();
            if(ens.getHP() < 300) {
                enemySquad.add(ens);
            }
        }
    }
}
