package test;

import static org.junit.Assert.*;

import org.junit.Test;

import command.CmdShapeAdd;
import geometry.*;
import mvc.Model;

public class Test_CmdShape {
	
	@Test
	public void CmdShapeAdd_Succesful() {
		
		Model model = new Model();
		Point p1 = new Point(10, 10);
		
		//model.add(p1);
		
		CmdShapeAdd c1 = new CmdShapeAdd(model, p1);
		
		c1.execute();
		
		assertEquals("(10,10)", model.get(0).toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void CmdShapeRemove_Succesful() {
		
		Model model = new Model();
		Point p1 = new Point(10, 10);
		
		//model.add(p1);
		
		CmdShapeAdd cmdShapeAdd = new CmdShapeAdd(model, p1);
		
		cmdShapeAdd.execute();
		
		System.out.println(model.get(0).toString());

	}
	
	
}
