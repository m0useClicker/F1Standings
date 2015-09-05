package m0useclicker.f1standings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableRow;

/**
 * TableRow with cell that describes pilot standings row
 */
class PilotTableRow extends TableRow {
    private final int pilotPosition;
    private final String pilotName;
    private final String pilotNationality;
    private final String pilotTeam;
    private final int pilotPoints;

    public PilotTableRow(Context context, int position, String name, String nationality, String team, int points) {
        super(context);

        pilotPosition = position;
        pilotName = name;
        pilotNationality = nationality;
        pilotTeam = team;
        pilotPoints = points;

        AddCells();
    }

    private void AddCells() {
        Context context = getContext();

        TextViewGrayText positionLabel = new TextViewGrayText(context);
        positionLabel.setText(String.valueOf(pilotPosition));
        positionLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(positionLabel);

        TextViewGrayText nameLabel = new TextViewGrayText(context);
        nameLabel.setText(pilotName);
        nameLabel.setTypeface(Typeface.DEFAULT_BOLD);
        addView(nameLabel);

        TextViewGrayText nationalityLabel = new TextViewGrayText(context);
        nationalityLabel.setText(pilotNationality);
        addView(nationalityLabel);

        TextViewGrayText teamLabel = new TextViewGrayText(context);
        teamLabel.setText(pilotTeam);
        addView(teamLabel);

        TextViewGrayText pointsLabel = new TextViewGrayText(context);
        pointsLabel.setText(String.valueOf(pilotPoints));
        addView(pointsLabel);
    }
}
