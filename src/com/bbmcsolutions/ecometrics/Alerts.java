package com.bbmcsolutions.ecometrics;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Alerts {
public static void ShowEmpAddedAlert(Context con)
{
	AlertDialog.Builder builder=new AlertDialog.Builder(con);
	builder.setTitle("Add new Metric");
	builder.setIcon(android.R.drawable.ic_dialog_info);
	DialogListner listner=new DialogListner();
	builder.setMessage("Metric Added successfully");
	builder.setPositiveButton("ok", listner);
	
	AlertDialog diag=builder.create();
	diag.show();
}

public static AlertDialog ShowEditDialog(final Context con,final Metric m)
{
	AlertDialog.Builder b=new AlertDialog.Builder(con);
	b.setTitle("Metric Details");
	LayoutInflater li=LayoutInflater.from(con);
	View v=li.inflate(R.layout.editdialog, null);
	
	b.setIcon(android.R.drawable.ic_input_get);
	
	b.setView(v);
	final TextView txtLoc=(TextView)v.findViewById(R.id.txtDelLoc);
	final TextView txtVal=(TextView)v.findViewById(R.id.txtDelVal);
	final Spinner spin=(Spinner)v.findViewById(R.id.spinDiagType);
	Utilities.ManageTypeSpinner(con, spin);
	for(int i=0;i<spin.getCount();i++)
	{
		long id=spin.getItemIdAtPosition(i);
		if(id==m.getType())
		{
			spin.setSelection(i, true);
			break;
		}
	}
	
	
	txtLoc.setText(m.getLoc());
	txtVal.setText(String.valueOf(m.getVal()));
	
	b.setPositiveButton("Modify", new OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			m.setLoc(txtLoc.getText().toString());
			m.setVal(Integer.valueOf(txtVal.getText().toString()));
			m.setType((int)spin.getItemIdAtPosition(spin.getSelectedItemPosition()));
			
			try
			{
			DataBaseHelper db=new DataBaseHelper(con);
			db.UpdateMetric(m);
			
			}
			catch(Exception ex)
			{
				CatchError(con, ex.toString());
			}
		}
	});
	
	b.setNeutralButton("Delete", new OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			DataBaseHelper db=new DataBaseHelper(con);
			db.DeleteMetric(m);
		}
	});
	b.setNegativeButton("Cancel", null);
	
	return b.create();
	//diag.show();
	
}

static public void CatchError(Context con, String Exception)
{
	Dialog diag=new Dialog(con);
	diag.setTitle("Error");
	TextView txt=new TextView(con);
	txt.setText(Exception);
	diag.setContentView(txt);
	diag.show();
}


}



