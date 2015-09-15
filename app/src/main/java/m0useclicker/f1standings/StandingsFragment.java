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
import android.widget.TableLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class StandingsFragment extends Fragment {
    final String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    final String pilotsUrl = "http://www.formula1.com/content/fom-website/en/championship/results/" + year + "-driver-standings.html";
    final String teamsUrl = "http://www.formula1.com/content/fom-website/en/championship/results/" + year + "-constructor-standings.html";
    Context context;
    View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();

        Bundle args = getArguments();
        int fragmentType = args.getInt("pageType");
        if (fragmentType == 0) {
            mainView = inflater.inflate(R.layout.pilots_standings_fragment, container, false);
            new PilotsStandingsPageParser().execute(pilotsUrl);
        } else if (fragmentType == 1) {
            mainView = inflater.inflate(R.layout.teams_standings_fragment, container, false);
            new TeamsStandingsPageParser().execute(teamsUrl);
        } else {
            showErrorDialog();
        }

        return mainView;
    }

    private void fillPilotsTable(Set<Pilot> pilots) {
        TableLayout table = (TableLayout) mainView.findViewById(R.id.pilots);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        for (Pilot pilot : pilots) {
            PilotTableRow pilotRow = new PilotTableRow(context, pilot.getPosition(), pilot.getName(), pilot.getNationality(), pilot.getTeam(), pilot.getPoints());
            table.addView(pilotRow);
        }
    }

    private void fillTeamsTable(Set<Team> teams){
        TableLayout table = (TableLayout) mainView.findViewById(R.id.teams);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        for (Team team : teams) {
            TeamTableRow teamRow = new TeamTableRow(context, team.getPosition(),team.getName(),team.getPoints());
            table.addView(teamRow);
        }
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Unable to load data.")
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
            else
                fillPilotsTable(pilots);
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
            else
                fillTeamsTable(teams);
        }
    }
}
