package com.jonnathan.gallegos.examenfinal_jonnathangallegos.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jonnathan.gallegos.examenfinal_jonnathangallegos.AgregarRegistro;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Menu;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.Usuario;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.R;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.detailUser;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord>{

    //Variables
    private Context context;
    private ArrayList<Usuario> recordsList;
    //Constructor

    public static String ci = null;

    public AdapterRecord(Context context, ArrayList<Usuario> recordsList){
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {

        Usuario model = recordsList.get(position);
        final int id = model.getId_usuario();

        holder.cedula.setText(model.getCedula());
        holder.nombre.setText(model.getNombre());
        holder.contra.setText(model.getContrasenia());
        holder.permiso.setText(model.getPermisos());

        if (model.getFoto().equals("null")){
            holder.profileIv.setImageResource(R.drawable.mishi);
        }else {
            holder.profileIv.setImageURI(Uri.parse(model.getFoto()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detailUser.class);
                intent.putExtra("CEDULA", model.getCedula());
                intent.putExtra("NOMBRE", model.getNombre());
                intent.putExtra("CONTRA", model.getContrasenia());
                intent.putExtra("PERMISO", model.getPermisos());
                intent.putExtra("FOTO", model.getFoto());
                context.startActivity(intent);
            }
        });

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsEditOrDetele( model.getCedula(), model.getNombre(), model.getContrasenia(), model.getPermisos(), model.getFoto());
            }
        });
    }

    public void OptionsEditOrDetele(String cedulaU, String nombresU, String cotraseniaU, String permisosU, String photoU){
        String [] optionC_D = {"Modificar", "Eliminar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(optionC_D, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent intent = new Intent(context, AgregarRegistro.class);
                    intent.putExtra("CEDULA", cedulaU);
                    intent.putExtra("NOMBRE", nombresU);
                    intent.putExtra("CONTRA", cotraseniaU);
                    intent.putExtra("PERMISO", permisosU);
                    intent.putExtra("FOTO", photoU);
                    intent.putExtra("EDIT_USER", true);
                    context.startActivity(intent);
                }else if( which == 1){
                    ci = cedulaU;
                    System.out.println("Damos en regreso del adapter -> "+ci);
                    ((Menu)context).onResume();
                }
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        //vistas
        ImageView profileIv;
        TextView cedula, nombre, contra, permiso;
        ImageButton moreBtn;
        public HolderRecord(@NonNull View itemView){
            super(itemView);

            //Inicializamos la vistas
            profileIv = itemView.findViewById(R.id.profileIv);
            cedula = itemView.findViewById(R.id.cedulaView);
            nombre = itemView.findViewById(R.id.nombreView);
            contra = itemView.findViewById(R.id.contraView);
            permiso = itemView.findViewById(R.id.permisoView);
            moreBtn = itemView.findViewById(R.id.moreBtn);

        }
    }
}
