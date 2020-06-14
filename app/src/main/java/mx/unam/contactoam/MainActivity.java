package mx.unam.contactoam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

//import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    //inicializamos las variables del TesxtInputEditText, el calendario, dia, mes y año.
    private TextInputLayout tilyContacto, tilyTelefono, tilyEmail, tilyDescripcion;
    private EditText tietContacto, tietFechadenac, tietTelefono, tietEmail, tietDescripcion;
    Calendar nFecha;
    private int dia, mes, anio, validarbundle=0;
    Button btnSiguiente;
    private String contacto, fechanac, telefono, email, descripcion, fecha, sdia, smes, sanio;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Identificamos todos los TextInputLayout y los asignamos a unas variables TextInputLayout
        tilyContacto = findViewById(R.id.tilyContacto);
        //tilyFechadenac = findViewById(R.id.tilyFechadenac);
        tilyTelefono = findViewById(R.id.tilyTelefono);
        tilyEmail = findViewById(R.id.tilyEmail);
        tilyDescripcion = findViewById(R.id.tilyDescripcion);
        //Identificamos todos los TextInputEditText y los asignamos a unas variables EditText( o TextInputEditText)
        tietContacto= findViewById(R.id.tietContacto);
        tietFechadenac= findViewById(R.id.tietFechadenac);
        tietTelefono= findViewById(R.id.tietTelefono);
        tietEmail= findViewById(R.id.tietEmail);
        tietDescripcion= findViewById(R.id.tietDescripcion);
        //Identificamos el boton siguiente
        btnSiguiente = findViewById(R.id.btnSiguiente);
        //metodo usado para recibir los datos de la clase ConfirmarAM
        datosBundle();
        //preguntamos si el Bundle tiene datos null( que no ha llegado informacion) y produce un error
        if (validarbundle==1)
        {   //hacemos un llamado a la funcion fechaActual(), para enviarla el tietFechadenac
            fechaActual();
            //le agregamos al TextInputEditText, la fecha por defecto( actual )
            tietFechadenac.setText(fecha);
        }else{
               //usamos la funcionformatoFecha() para acomodar los datos recibidos de la clase ConfirmacionAM
               formatoFechaAEditar();
               tietContacto.setText(contacto);
               tietFechadenac.setText(fechanac);
               tietTelefono.setText(telefono);
               tietEmail.setText(email);
               tietDescripcion.setText(descripcion);
             }

        //Detectamos si se ha realizado un click en el TextInputEditText
        tietFechadenac.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)//aqui se ha generado una notacion que indica que el DatePickerDialog necesita un minimo de API
            @Override
            public void onClick(View v)
            {   //creamos un DatePickerDialog para interactuar e indicar la fecha deseada
                DatePickerDialog dpdFecha = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        sdia = Integer.toString(dayOfMonth);
                        smes = Integer.toString(month+1);
                        sanio = Integer.toString(year);
                        fecha = sdia+" / "+smes+" / "+sanio;
                        tietFechadenac.setText(fecha);
                    }
                },anio,mes,dia);
                dpdFecha.show();
            }
        });

        //Creamos el acceso para cuando a este se le haga click
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),getResources().getString(R.string.mensaje1),Toast.LENGTH_LONG).show();
                if(validar_campocontacto())
                {
                    //Leemos todos los datos que estan en los TextInputEditText y los asignamos a unas variables EditText( o TextInputEditText)
                    contacto = tietContacto.getText().toString().trim()+"";
                    fechanac = tietFechadenac.getText().toString().trim()+"";
                    telefono = tietTelefono.getText().toString().trim()+"";
                    email = tietEmail.getText().toString().trim()+"";
                    descripcion = tietDescripcion.getText().toString().trim()+"";
                    //pasamos los datos a el siguiente activity( ConfirmacionAM), para confirmar estos datos.
                    Intent intentContacto = new Intent(MainActivity.this, ConfirmacionAM.class);
                    intentContacto.putExtra("contacto",contacto);
                    intentContacto.putExtra("sdia",sdia);
                    intentContacto.putExtra("smes",smes);
                    intentContacto.putExtra("sanio",sanio);
                    intentContacto.putExtra("telefono",telefono);
                    intentContacto.putExtra("email",email);
                    intentContacto.putExtra("descripcion",descripcion);
                    startActivity(intentContacto);//iniciamos el intent
                    finish();//finalizamos( cerramos ) la activity( MainActivity ), para liberar memoria
                }
            }
        });
    }
    //metodo para posicionarte en el TextInputEditText donde este el error
    public void solicitudFocus(View v)
    {
        if(v.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //metodo para validar que los campos contengan datos( no vacios ) y un minimo de caracteres
    public boolean validar_campocontacto()
    {   //validar Contacto
        if(tietContacto.getText().toString().trim().isEmpty())
        {
            tilyContacto.setError("Debe ingresar el Nombre");
            solicitudFocus(tietContacto);
            return false;
        }else if(tietContacto.getText().toString().trim().length()<3)
               {
                   tilyContacto.setError("Minimo 3 caracteres");
                   solicitudFocus(tietContacto);
                   return false;
               }else {
                       tilyContacto.setErrorEnabled(false);
                     }
        //Toast.makeText(getBaseContext(),getResources().getString(R.string.mensaje1),Toast.LENGTH_LONG).show();
        //validar Telefono
        if(tietTelefono.getText().toString().trim().isEmpty())
        {
            tilyTelefono.setError("Debe ingresar el numero de Telefono");
            solicitudFocus(tietTelefono);
            return false;
        }else if(tietTelefono.getText().toString().trim().length()<10)
                {
                    tilyTelefono.setError("El numero debe tener 10 digitos");
                    solicitudFocus(tietTelefono);
                    return false;
                }else {
                         tilyTelefono.setErrorEnabled(false);
                        }
        //validar email
        if(tietEmail.getText().toString().trim().isEmpty())
        {
            tilyEmail.setError("Debe ingresar el Email");
            solicitudFocus(tietEmail);
            return false;
        }else if(tietEmail.getText().toString().trim().length()<11)
                {
                    tilyEmail.setError("El Email debe tener como minimo 11 caracteres");
                    solicitudFocus(tietEmail);
                    return false;
                }else {
                        tilyEmail.setErrorEnabled(false);
                      }
        //validar Descripcion
        if(tietDescripcion.getText().toString().trim().isEmpty())
        {
            tilyDescripcion.setError("Debe ingresar una Descripcion");
            solicitudFocus(tietDescripcion);
            return false;
        }else if(tietDescripcion.getText().toString().trim().length()<1)
                {
                    tilyDescripcion.setError("Minimo 1 caracter");
                    solicitudFocus(tietDescripcion);
                    return false;
                }else {
                        tilyDescripcion.setErrorEnabled(false);
                      }
        //retornamos true si todos los campos estan correctos
        return true;
    }

    public void fechaActual()
    {
        //capturamos el dia, mes y año actual para colocarlos en el TextInputEditText, como referencia.
        nFecha = Calendar.getInstance();
        dia = nFecha.get(Calendar.DAY_OF_MONTH);
        mes = nFecha.get(Calendar.MONTH);
        anio = nFecha.get(Calendar.YEAR);
        //le agregamos un dia mas al mes porque los meses en este formato comienzan en cero 0
        sdia = Integer.toString(dia);
        smes = Integer.toString(mes+1);
        sanio = Integer.toString(anio);
        fecha = sdia+" / "+smes+" / "+sanio;
    }

    public void formatoFechaAEditar()
    {
        //pasamos los datos de dia, mes y año, de string a enteros, para ser pasados al calendario
        dia = Integer.parseInt(sdia);
        mes = Integer.parseInt(smes);
        anio = Integer.parseInt(sanio);
        //le restamos uno al mes, ya que los meses se cargan desde cero y ya se le habia agregado uno
        mes = mes-1;
        //se anexa el formato de la fecha para pasarsela al TextInputEditText
        fechanac = sdia+" / "+smes+" / "+sanio;
    }

    public void datosBundle()
    {
        //creamos un Bundle para recibir los parametros a editar o valores enviados desde ConfirmacionAM y
        //le validamos por si da un error
        try {
            Bundle parametrosaeditar = getIntent().getExtras();
            if(parametrosaeditar == null)
            {
                validarbundle=1;
                return;
            }

            contacto = parametrosaeditar.getString("editcontacto");
            sdia = parametrosaeditar.getString("editsdia");
            smes = parametrosaeditar.getString("editsmes");
            sanio = parametrosaeditar.getString("editsanio");
            telefono = parametrosaeditar.getString("edittelefono");
            email = parametrosaeditar.getString("editemail");
            descripcion = parametrosaeditar.getString("editdescripcion");
            //Toast.makeText(getBaseContext(),getResources().getString(R.string.mensaje1),Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            validarbundle=1;
        }
    }

}
