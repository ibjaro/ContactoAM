package mx.unam.contactoam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ConfirmacionAM extends AppCompatActivity
{

    TextView txvConfircontacto, txvConfirfechanac, txvConfirtelefono, txvConfiremail, txvConfirdescripcion;
    String contacto, fechanac, telefono, email, descripcion, sdia, smes, sanio;
    private int validarbundle=0;
    Button btnEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_a_m);

        //Identificamos todos los TextView, que mostraran los datos recibidos, y los asignamos a unas variables TextView
        txvConfircontacto = findViewById(R.id.txvConfircontacto);
        txvConfirfechanac = findViewById(R.id.txvConfirfechanac);
        txvConfirtelefono = findViewById(R.id.txvConfirtelefono);
        txvConfiremail = findViewById(R.id.txvConfiremail);
        txvConfirdescripcion = findViewById(R.id.txvConfirdescipcion);
        //Identificamos el boton editar
        btnEditar = findViewById(R.id.btnEditar);

        //metodo usado para recibir los datos de la clase mainActivity
        datosBundle();
        //preguntamos si el Bundle tiene datos null( que no ha llegado informacion) y produce un error
        if(validarbundle==0)
        {
            //acomodamos los datos de la fecha recibidos de la clase MainActivity
            fechanac = sdia+" / "+smes+" / "+sanio;
            //agreagamos los datos recibidos a los TextView
            txvConfircontacto.setText(contacto);
            txvConfirfechanac.setText(fechanac);
            txvConfirtelefono.setText(telefono);
            txvConfiremail.setText(email);
            txvConfirdescripcion.setText(descripcion);
        }

        //Creamos el acceso para cuando a este se le haga click
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo usado para realizar el intent
                intentEditar();
            }
        });
    }

    public void datosBundle()
    {
        //creamos un Bundle para recibir los parametros a confirmar o valores enviados desde el MainActivity
        Bundle parametrosaconfirmar = getIntent().getExtras();
        if(parametrosaconfirmar == null)
        {
            validarbundle=1;
            return;
        }
        contacto = parametrosaconfirmar.getString("contacto");
        sdia = parametrosaconfirmar.getString("sdia");
        smes = parametrosaconfirmar.getString("smes");
        sanio = parametrosaconfirmar.getString("sanio");

        //fechanac = parametrosaconfirmar.getString("fechanac");
        telefono = parametrosaconfirmar.getString("telefono");
        email = parametrosaconfirmar.getString("email");
        descripcion = parametrosaconfirmar.getString("descripcion");
    }

    public void intentEditar()
    {
        //pasamos los datos a el MainActivity, para que se editen.
        Intent intentEditar = new Intent(ConfirmacionAM.this,MainActivity.class);
        intentEditar.putExtra("editcontacto",contacto);
        intentEditar.putExtra("editsdia",sdia);
        intentEditar.putExtra("editsmes",smes);
        intentEditar.putExtra("editsanio",sanio);
        intentEditar.putExtra("edittelefono",telefono);
        intentEditar.putExtra("editemail",email);
        intentEditar.putExtra("editdescripcion",descripcion);
        startActivity(intentEditar);//iniciamos el intent
        finish();//finalizamos( cerramos ) la activity( ConfirmacionAM ), para liberar memoria
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            intentEditar();
        }
        return super.onKeyDown(keyCode, event);
    }
}
