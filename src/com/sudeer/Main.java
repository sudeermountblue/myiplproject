package com.sudeer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Main {
    public static final int ID = 0;
    public static final int SEASON = 1;
    public static final int TEAM1 = 4;
    public static final int TEAM2 = 5;
    public static final int TOSS_WINNER = 6;
    public static final int WINNER = 10;
    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYear(matches);
        findNumberOfMatchesWonPerTeamInallSeasons(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
        findMostEconomicalBowlerIn2015(matches, deliveries);
        findNumberOfTossesWonByEachTeam(matches);
    }
    private static void findMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> mostEconomicalBowlerIn2015 = new HashMap<>();
        List<String> matchIds = new ArrayList<>();
        for (int i = 0; i < matches.size(); i++) {
            String season = matches.get(i).getSeason();
            if (season.equals("2015")) {
                matchIds.add(matches.get(i).getId());
            }
        }
        HashMap<String, Integer> bowlerRuns = new HashMap();
        HashMap<String, Integer> bowlerBalls = new HashMap();

        for (int j = 0; j < deliveries.size(); j++) {
            String deliveryId1 = deliveries.get(j).getMatchId();
            if (matchIds.contains(deliveryId1) && deliveries.get(j).getIsSuperOver().equals("0")) {
                if (Integer.valueOf(deliveries.get(j).getWideRuns()) == 0 && Integer.valueOf(deliveries.get(j).getNoBallRuns()) == 0) {
                    String bowler = deliveries.get(j).getBowler();
                    if (bowlerBalls.containsKey(bowler)) {
                        bowlerBalls.put(bowler, bowlerBalls.get(bowler) + 1);
                    } else {
                        bowlerBalls.put(bowler, 1);
                    }
                }
                int totalRuns = Integer.valueOf(deliveries.get(j).getTotalRuns());
                int byeRuns = Integer.valueOf(deliveries.get(j).getByeRuns());
                int legByeRuns = Integer.valueOf(deliveries.get(j).getLegByeRuns());
                String bowler1 = deliveries.get(j).getBowler();

                int runs = Integer.valueOf(totalRuns - byeRuns - legByeRuns);
                if (bowlerRuns.containsKey(bowler1)) {
                    bowlerRuns.put(bowler1, bowlerRuns.get(bowler1) + runs);
                } else {
                    bowlerRuns.put(bowler1, runs);
                }
            }
        }
        HashMap<String, Float> bowlerEconomies = new HashMap();
        List<String> bowlers = new ArrayList<>(bowlerBalls.keySet());

        for (int i = 0; i < bowlers.size(); i++) {
            String bowler = bowlers.get(i);
            float overs = bowlerBalls.get(bowler) / 6;
            float economy = bowlerRuns.get(bowler) / overs;
            bowlerEconomies.put(bowler, economy);
        }
        HashMap<String, Float> sortedBowlerEconomies = sortByValue(bowlerEconomies);
        Map.Entry<String, Float> entry = sortedBowlerEconomies.entrySet().iterator().next();
        String key = entry.getKey();
        Float value = entry.getValue();
        System.out.println("For the year 2015 the top economical bowler ---- Name : " + key + "  -- Economy : " + value);
    }
    private static HashMap<String, Float> sortByValue(HashMap<String, Float> sortedBowlers) {
        List<Map.Entry<String, Float>> sortedList = new LinkedList<Map.Entry<String, Float>>(sortedBowlers.entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> object1,
                               Map.Entry<String, Float> object2) {
                return (object1.getValue()).compareTo(object2.getValue());
            }
        });
        HashMap<String, Float> sortedEconomy = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float>  listObject: sortedList) {
            sortedEconomy.put(listObject.getKey(), listObject.getValue());
        }
        return sortedEconomy;
    }
    private static void findNumberOfTossesWonByEachTeam(List<Match> matches) {
        HashMap<String, Integer> numberOfTossesWonPerTeam = new HashMap<>();
        for (int i = 0; i < matches.size(); i++) {
            String team = matches.get(i).getTossWinner();
            if (numberOfTossesWonPerTeam.containsKey(team)) {
                int count = (int) numberOfTossesWonPerTeam.get(team);
                numberOfTossesWonPerTeam.put(team, count += 1);
            } else {
                numberOfTossesWonPerTeam.put(team, 1);
            }
        }
        System.out.println(("numberOfTossesWonPerTeam"));
        System.out.println(numberOfTossesWonPerTeam);
    }
    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> extraRunsConcededPerTeam = new HashMap<>();
        List<String> matchIds = new ArrayList<>();
        for (int i = 0; i < matches.size(); i++) {
            String season = matches.get(i).getSeason();

            if (season.equals("2016")) {
                matchIds.add(matches.get(i).getId());
            }
        }
        for (int j = 0; j < deliveries.size(); j++) {

            String deliviriesId = deliveries.get(j).getMatchId();
            if (matchIds.contains(deliviriesId)) {
                String team = deliveries.get(j).getBowlingTeam();
                if (extraRunsConcededPerTeam.containsKey(team)) {
                    int count = extraRunsConcededPerTeam.get(team);
                    extraRunsConcededPerTeam.put(team, count + Integer.valueOf(deliveries.get(j).getExtraRuns()));
                } else {
                    extraRunsConcededPerTeam.put(team, Integer.valueOf(deliveries.get(j).getExtraRuns()));
                }
            }
        }
        System.out.println("extraRunsConcededPerTeam");
        System.out.println(extraRunsConcededPerTeam);
    }
    private static void findNumberOfMatchesWonPerTeamInallSeasons(List<Match> matches) {
        HashMap<String, Integer> numberOfMatchesWonPerTeam = new HashMap<>();
        for (int i = 0; i < matches.size(); i++) {
            String team = matches.get(i).getWinner();
            if (numberOfMatchesWonPerTeam.containsKey(team)) {
                int count = (int) numberOfMatchesWonPerTeam.get(team);
                numberOfMatchesWonPerTeam.put(team, count += 1);

            } else {
                numberOfMatchesWonPerTeam.put(team, 1);
            }
        }
        System.out.println(("numberOfMatchesWonPerTeam"));
        System.out.println(numberOfMatchesWonPerTeam);
    }
    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        HashMap<String, Integer> totalMatchesPlayedPerYear = new HashMap<>();
        for (int i = 0; i < matches.size(); i++) {
            String year = matches.get(i).getSeason();
            if (totalMatchesPlayedPerYear.containsKey(year)) {
                int count = (int) totalMatchesPlayedPerYear.get(year);
                totalMatchesPlayedPerYear.put(year, count += 1);
            } else {
                totalMatchesPlayedPerYear.put(year, 1);
            }
        }
        System.out.println("NumberOfMatchesPlayedPerYear");
        System.out.println(totalMatchesPlayedPerYear);
    }
    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();
        String path = "src/com/files/deliveries.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setMatchId(values[0]);
                delivery.setBowlingTeam(values[3]);
                delivery.setOver(values[4]);
                delivery.setBall(values[5]);
                delivery.setBowler(values[8]);
                delivery.setIsSuperOver(values[9]);
                delivery.setWideRuns(values[10]);
                delivery.setByeRuns(values[11]);
                delivery.setLegByeRuns(values[12]);
                delivery.setNoBallRuns(values[13]);
                delivery.setExtraRuns(values[16]);
                delivery.setTotalRuns(values[17]);

                deliveries.add(delivery);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deliveries;
    }
    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();
        String path = "src/com/files/matches.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");
                Match match = new Match();
                match.setId(values[ID]);
                match.setSeason(values[SEASON]);
                match.setTeam1(values[TEAM1]);
                match.setTeam2(values[TEAM2]);
                match.setTossWinner(values[TOSS_WINNER]);
                match.setWinner(values[WINNER]);

                matches.add(match);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
