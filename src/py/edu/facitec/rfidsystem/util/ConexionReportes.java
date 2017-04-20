package py.edu.facitec.rfidsystem.util;

import java.io.InputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ConexionReportes<E> {
	
	public JasperViewer viewer;

	public void GerarRealatorio(List<E> lista, String reporte) throws JRException{
		InputStream stream = ConexionReportes.class.getResourceAsStream("/py/edu/facitec/rfidsystem/informe/"+reporte+".jrxml");
		JasperReport report= JasperCompileManager.compileReport(stream);
		JasperPrint print = JasperFillManager.fillReport(report,null,new JRBeanCollectionDataSource(lista));
		viewer = new JasperViewer(print,false);
		
	}
}
