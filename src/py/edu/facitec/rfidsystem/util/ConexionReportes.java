package py.edu.facitec.rfidsystem.util;

import java.io.InputStream;
import java.util.List;

import javax.swing.JDialog;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ConexionReportes<E> {

	public JDialog viewer = new JDialog(new javax.swing.JFrame(),"Visualizar Reporte", true);
	
	public void GerarRealatorio(List<E> lista, String reporte) throws JRException{     
		viewer.setSize(1000,700);     
		viewer.setLocationRelativeTo(null);
		viewer.setModal(true);
		InputStream stream = ConexionReportes.class.getResourceAsStream("/py/edu/facitec/rfidsystem/informe/"+reporte+".jrxml");
		JasperReport report= JasperCompileManager.compileReport(stream);
		JasperPrint print = JasperFillManager.fillReport(report,null,new JRBeanCollectionDataSource(lista));
		JasperViewer jrViewer = new JasperViewer(print, true);     
		viewer.getContentPane().add(jrViewer.getContentPane());
		
		
	}
}
