package Math;

import java.awt.Point;

import Physics.AABB;
import Physics.CollisionData;

public class MathMethods {
	
	public static float Clamp(float min, float max, float val) {
		if(val < min) {return min;}
		if(val > max) {return max;}
		return val;
	}
	
	public static float Distance(Vector2 a, Vector2 b) {
		float dx = a.getX() - b.getX();
        float dy = a.getY() - b.getY();
        return (float)Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public static float DistanceToSqr(Vector2 a, Vector2 b) {
		float dx = a.getX() - b.getX();
        float dy = a.getY() - b.getY();
        return (dx * dx) + (dy * dy);
	}
	
	public static float angleFacing(int x1, int y1, int x2, int y2) {
		return (float) Math.atan2(y2 - y1, x2 - x1);
	}
	
	public static float angleFacingDegrees(int x1, int y1, int x2, int y2) {
		return (float)Math.toDegrees(angleFacing(x1, y1, x2, y2));
	}
	
	public static Point angleForward(int x, int y, float angRadians, float dist) {
		x += Math.cos(angRadians) * dist;
		y += Math.sin(angRadians) * dist;
		return new Point(x,y);
	}
	
	public static Vector2 normalVector(Vector2 start, Vector2 end) {
		float norm = angleFacing(start.getX(), start.getY(), end.getX(), end.getY());
		return new Vector2((float)Math.cos(norm), (float)Math.sin(norm));
	}
	
	public static float normalVectorDot(Vector2 start, Vector2 end) {
		return start.subtract(end).dot(normalVector(start, end));
	}

	// Colliders //
	
	public static boolean AABBVsAABB(AABB a, AABB b) {
		if(a.getMaxRelative().getX() < b.getMinRelative().getX() || a.getMinRelative().getX() > b.getMaxRelative().getX()) {return false;}
		if(a.getMaxRelative().getY() < b.getMinRelative().getY() || a.getMinRelative().getY() > b.getMaxRelative().getY()) {return false;}
		return true;
	}
	
	public static CollisionData CircleVsCircle(Vector2 originA, int diameterA, Vector2 originB, int diameterB) {
		float totalDiameter = (diameterA / 2) + (diameterB / 2);
		float dist = DistanceToSqr(originA, originB);
		boolean collided = dist < totalDiameter * totalDiameter;
		
		if(!collided) {
			return new CollisionData();
		}
		
		float norm = angleFacing(originA.getX(), originA.getY(), originB.getX(), originB.getY());
		float distSqr = (float)Math.sqrt(dist);
		float depth = (diameterA / 2) + (diameterB / 2) - distSqr;
		Point loc = angleForward(originA.getX(), originA.getY(), norm, (int)((diameterA / 2) - depth));
		
		return new CollisionData(norm, depth, loc.x, loc.y);
	}
	
	public static boolean PointVsCircle(Point point, Vector2 origin, int diameter) {
		float dist = DistanceToSqr(new Vector2(point.x, point.y), origin);
		return dist < (diameter / 2) * (diameter / 2);
	}
	
	public static boolean PointVsSquare(Point point, Vector2 origin, Vector2 size) {
		if(point.getX() < origin.getX() || point.getX() > origin.getX() + size.getX()) {return false;}
		if(point.getY() < origin.getY() || point.getY() > origin.getY() + size.getY()) {return false;}
		return true;
	}
	
	public static Vector2 ClosestPointSquare(Vector2 point, Vector2 squareCenter, Vector2 squareSize) {
		Vector2 n = new Vector2(point.getX() - squareCenter.getX(), point.getY() - squareCenter.getY());
		float x_extent = (squareCenter.getX() + squareSize.getX() - squareCenter.getX()) / 2;
		float y_extent = (squareCenter.getY() + squareSize.getY() - squareCenter.getY()) / 2;
		n.setX(Clamp(-x_extent, x_extent, n.getXFloat()));
		n.setY(Clamp(-y_extent, y_extent, n.getYFloat()));
		return n;
	}
	
	public static CollisionData CircleVsSquare(Vector2 circleOrigin, int diameter, Vector2 squareCenter, Vector2 squareSize) {
		Vector2 closest = ClosestPointSquare(circleOrigin, squareCenter, squareSize);
		
		if(PointVsCircle(new Point(squareCenter.getX() + closest.getX(), squareCenter.getY() + closest.getY()), circleOrigin, diameter)) {
			float norm = angleFacing(squareCenter.getX() + closest.getX(), squareCenter.getY() + closest.getY(), circleOrigin.getX(), circleOrigin.getY()) + (float)Math.toRadians(180);
			float distSqr = Distance(new Vector2(squareCenter.getX() + closest.getX(), squareCenter.getY() + closest.getY()), circleOrigin);
			float depth = (diameter / 2) - distSqr;
			return new CollisionData(norm, depth, squareCenter.getX() + closest.getX(), squareCenter.getY() + closest.getY());
		}
		
		return new CollisionData();
	}
}
