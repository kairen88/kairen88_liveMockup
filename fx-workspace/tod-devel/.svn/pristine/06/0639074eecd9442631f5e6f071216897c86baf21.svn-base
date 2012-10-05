/*
 * Created on Jun 30, 2005
 */
package zz.csg.test;

import zz.csg.api.constraints.Constraint;
import zz.csg.api.constraints.Constraint.Type;
import zz.csg.api.constraints.ConstraintSystem;
import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.impl.constraints.ConstrainedRectangle;
import junit.framework.TestCase;

public class ConstraintSystemTest extends TestCase
{
	public void testSystem()
	{
		Systems theSystems = new Systems();
	}
	
	private static class Systems
	{
		ConstraintSystem s1 = new ConstraintSystem("s1");
		ConstraintSystem s11 = new ConstraintSystem("s11");
		ConstraintSystem s12 = new ConstraintSystem("s12");
		
		IConstrainedRectangle r1 = new ConstrainedRectangle();

		IConstrainedRectangle r11 = new ConstrainedRectangle();
		IConstrainedRectangle r12 = new ConstrainedRectangle();

		IConstrainedRectangle r111 = new ConstrainedRectangle();
		IConstrainedRectangle r112 = new ConstrainedRectangle();
		IConstrainedRectangle r121 = new ConstrainedRectangle();
		IConstrainedRectangle r122 = new ConstrainedRectangle();

		Systems()
		{
			r111.pW().set(1.0);
			r111.pH().set(1.0);
			r112.pW().set(1.0);
			r112.pH().set(1.0);
			
			r121.pW().set(1.0);
			r121.pH().set(1.0);
			r122.pW().set(1.0);
			r122.pH().set(1.0);

			setupSystem(s11, r11, r111, r112);print();
			setupSystem(s12, r12, r121, r122);print();
			setupSystem(s1, r1, r11, r12);
			
			print();

			r111.pW().set(10.0);
			r111.pH().set(5.0);
			r112.pW().set(10.0);
			r112.pH().set(5.0);
			
			print();
			
			r121.pW().set(11.0);
			r121.pH().set(6.0);
			r122.pW().set(11.0);
			r122.pH().set(6.0);
			
			print();
		}
		
		void print()
		{
			System.out.println("r1: "+r1);
			
			System.out.println("r11: "+r11);
			System.out.println("r12: "+r12);
			
			System.out.println("r111: "+r111);
			System.out.println("r112: "+r112);
			System.out.println("r121: "+r121);
			System.out.println("r122: "+r122);
			
			System.out.println();
		}
	
		private void setupSystem(
				ConstraintSystem aSystem, 
				IConstrainedRectangle o,
				IConstrainedRectangle i1,
				IConstrainedRectangle i2)
		{
			// i2.x >= i1.x+ i1.w + 2
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			-1,			-1},
					new IConstrainedDouble[] 	{i2.pX(),	i1.pX(),	i1.pW()},
					new Type[]			 		{Type.VAR,	Type.VAR,	Type.CONST},
					-2,
					Constraint.Operator.GEQ));
	
			// i1.y = i2.y
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			-1},
					new IConstrainedDouble[] 	{i1.pY(),	i2.pY()},
					new Type[]			 		{Type.VAR,	Type.VAR},
					0.0,
					Constraint.Operator.EQ));
			
			// i1.x >= o.x + 2
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			-1},
					new IConstrainedDouble[] 	{i1.pX(),	o.pX()},
					new Type[]			 		{Type.VAR,	Type.CONST},
					-2,
					Constraint.Operator.GEQ));
			
			// i1.y >= o.y + 2
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			-1},
					new IConstrainedDouble[] 	{i1.pY(),	o.pY()},
					new Type[]			 		{Type.VAR,	Type.CONST},
					-2,
					Constraint.Operator.GEQ));
	
			// o.x + o.w >= i2.x+ i2.w + 2
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			1,			-1,			-1},
					new IConstrainedDouble[] 	{o.pX(),	o.pW(),		i2.pX(),	i2.pW()},
					new Type[]			 		{Type.CONST,Type.VAR,	Type.VAR,	Type.CONST},
					-2,
					Constraint.Operator.GEQ));
			
			// o.y + o.h >= i2.y+ i2.h + 2
			aSystem.addConstraint(new Constraint(
					new double[] 				{1,			1,			-1,			-1},
					new IConstrainedDouble[] 	{o.pY(),	o.pH(),		i2.pY(),	i2.pH()},
					new Type[]			 		{Type.CONST,Type.VAR,	Type.VAR,	Type.CONST},
					-2,
					Constraint.Operator.GEQ));
			
		}
	}	
}
