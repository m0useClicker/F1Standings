package m0useclicker.f1standings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableRow;

/**
 * TableRow with cells that describe team standings row
 */
class TeamTableRow extends TableRow {
    private final int teamPosition;
    private final String teamName;
    private final int teamPoints;

    public TeamTableRow(Context context, int position, String name, int points) {
        super(context);

        teamPosition = position;
        teamName = name;
        teamPoints = points;
        AddCells();
    }

    private void AddCells() {
        Context context = getContext();

        TextViewGrayText positionLabel = new TextViewGrayText(context);
        positionLabel.setText(String.valueOf(teamPosition));
        positionLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(positionLabel);

        TextViewGrayText nameLabel = new TextViewGrayText(context);
        nameLabel.setText(teamName);
        nameLabel.setTypeface(Typeface.DEFAULT_BOLD);
        addView(nameLabel);

        TextViewGrayText pointsLabel = new TextViewGrayText(context);
        pointsLabel.setText(String.valueOf(teamPoints));
        addView(pointsLabel);
    }
}
