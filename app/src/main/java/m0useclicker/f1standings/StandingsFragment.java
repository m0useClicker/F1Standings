package m0useclicker.f1standings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class StandingsFragment extends Fragment {
    final String year = "2015";
    final String pilotsUrl = "http://www.formula1.com/content/fom-website/en/championship/results/" + year + "-driver-standings.html";
    final String teamsUrl = "http://www.formula1.com/content/fom-website/en/championship/results/" + year + "-constructor-standings.html";
    private Pilot[] pilotsData = new Pilot[]{};
    private Team[] teamsData = new Team[]{};
    Context context;
    View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();

        Bundle args = getArguments();
        int fragmentType = args.getInt("pageType");
        if (fragmentType == 0) {
            mainView = inflater.inflate(R.layout.pilots_stangings, container, false);
            new PilotsStandingsPageParser().execute(pilotsUrl);
        } else if (fragmentType == 1) {
            mainView = inflater.inflate(R.layout.teams_standings, container, false);
            new TeamsStandingsPageParser().execute(teamsUrl);
        } else {
            showErrorDialog();
        }

        return mainView;
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.errorTitle)
                .setMessage(R.string.unableToLoadDataMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.stat_notify_error)
                .show();
    }

    class PilotsStandingsPageParser extends AsyncTask<String, String, Boolean> {
        private Set<Pilot> pilots = new TreeSet<>(new Comparator<Pilot>() {
            public int compare(Pilot a, Pilot b) {
                return ((Integer) a.getPosition()).compareTo(b.getPosition());
            }
        });

        @Override
        protected Boolean doInBackground(String... params) {
            Document document;
            try {
                document = Jsoup.connect(pilotsUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            Elements elements = document.getElementsByClass("standings").select("tbody");
            if (elements.size() != 1) {
                return false;
            }

            Element tableElement = elements.get(0);
            Elements rows = tableElement.getElementsByTag("tr");

            try {
                for (Element row : rows) {
                    pilots.add(getPilotFromTrElement(row));
                }
            } catch (Exception e) {
                Log.v("F1STANDINGS", e.getMessage());
            }
            return true;
        }

        private Pilot getPilotFromTrElement(Element tr) throws Exception {
            int position = Integer.parseInt(getCell(tr, "position").text());
            String name = getCell(getCell(tr, "name"), "first-name").text() + " " + getCell(getCell(tr, "name"), "last-name").text();
            //String shortName = getCell(getCell(tr, "name"), "tla").text();
            String country = getCell(tr, "country").text();
            String team = getCell(tr, "team").text();
            //IMG car;
            int points = Integer.parseInt(getCell(tr, "points").text());
            return new Pilot(position, name, country, team, points);
        }

        private Element getCell(Element element, String cellClass) throws Exception {
            Elements elementsByClass = element.getElementsByClass(cellClass);
            if (elementsByClass.size() != 1) {
                throw new UnexpectedNumberOfElementsFound("getElementsByClass(" + cellClass + ")", elementsByClass.size());
            }
            return elementsByClass.get(0);
        }

        @Override
        protected void onPostExecute(Boolean successfulExecution) {
            if (!successfulExecution)
                showErrorDialog();
            else{
                pilotsData = pilots.toArray(new Pilot[pilots.size()]);
                ListView listView = (ListView) mainView.findViewById(R.id.pilotsList);
                listView.setAdapter(new PilotsArrayAdapter());
            }
        }
    }

    class TeamsStandingsPageParser extends AsyncTask<String, String, Boolean> {
        private Set<Team> teams = new TreeSet<>(new Comparator<Team>() {
            public int compare(Team a, Team b) {
                return ((Integer) a.getPosition()).compareTo(b.getPosition());
            }
        });

        @Override
        protected Boolean doInBackground(String... params) {
            Document document;
            try {
                document = Jsoup.connect(teamsUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            Elements elements = document.getElementsByClass("standings").select("tbody");
            if (elements.size() != 1) {
                return false;
            }

            Element tableElement = elements.get(0);
            Elements rows = tableElement.getElementsByTag("tr");

            try {
                for (Element row : rows) {
                    teams.add(getTeamFromTrElement(row));
                }
            } catch (Exception e) {
                Log.v("F1STANDINGS", e.getMessage());
            }

            return true;
        }

        private Team getTeamFromTrElement(Element tr) throws Exception {
            int position = Integer.parseInt(getCell(tr, "position").text());
            String name = getCell(tr, "name").text();
            int points = Integer.parseInt(getCell(tr, "points").text());
            return new Team(position, name, points);
        }

        private Element getCell(Element element, String cellClass) throws Exception {
            Elements elementsByClass = element.getElementsByClass(cellClass);
            if (elementsByClass.size() != 1) {
                throw new UnexpectedNumberOfElementsFound("getElementsByClass(" + cellClass + ")", elementsByClass.size());
            }
            return elementsByClass.get(0);
        }

        @Override
        protected void onPostExecute(Boolean successfulExecution) {
            if (!successfulExecution)
                showErrorDialog();
            else {
                teamsData = teams.toArray(new Team[teams.size()]);
                ListView listView = (ListView) mainView.findViewById(R.id.teamsList);
                listView.setAdapter(new TeamsArrayAdapter());
            }
        }
    }

    class PilotsArrayAdapter extends ArrayAdapter<Pilot> {
        public PilotsArrayAdapter() {
            super(StandingsFragment.this.context, R.layout.pilot_row, pilotsData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Pilot pilot = pilotsData[position];

            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.pilot_row, parent, false);
            }

            TextView positionView = (TextView) convertView.findViewById(R.id.pilotPosition);
            positionView.setText(String.valueOf(pilot.getPosition()));

            TextView nameView = (TextView) convertView.findViewById(R.id.pilotName);
            nameView.setText(pilot.getName());

            TextView nationality = (TextView) convertView.findViewById(R.id.pilotNationality);
            nationality.setText(pilot.getNationality());

            TextView team = (TextView)convertView.findViewById(R.id.pilotTeam);
            team.setText(pilot.getTeam());

            TextView pointsView = (TextView) convertView.findViewById(R.id.pilotPoints);
            pointsView.setText(String.valueOf(pilot.getPoints()));

            return convertView;
        }
    }

    class TeamsArrayAdapter extends ArrayAdapter<Team>{
        public TeamsArrayAdapter(){
            super(StandingsFragment.this.context,R.layout.team_row,teamsData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Team team = teamsData[position];

            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.team_row, parent, false);
            }

            TextView teamPosition = (TextView) convertView.findViewById(R.id.teamPosition);
            teamPosition.setText(String.valueOf(team.getPosition()));

            TextView name = (TextView) convertView.findViewById(R.id.teamName);
            name.setText(team.getName());

            TextView points = (TextView) convertView.findViewById(R.id.teamPoints);
            points.setText(String.valueOf(team.getPoints()));

            return convertView;
        }
    }
}
