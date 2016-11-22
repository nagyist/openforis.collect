package org.openforis.collect.io.metadata.samplingpointdata;

import static org.openforis.idm.metamodel.SpatialReferenceSystem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openforis.collect.metamodel.SurveyViewGenerator.SurveyView.Distribution;
import org.openforis.collect.model.SamplingDesignItem;
import org.openforis.idm.geospatial.CoordinateUtils;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.model.Coordinate;

/**
 * 
 * @author S. Ricci
 * @author G. Johnson
 *
 */
public class SamplingPointDataGenerator {

	public static final SpatialReferenceSystem WEB_MARCATOR_SRS = new SpatialReferenceSystem(
			"EPSG:3857",
			"PROJCS[\"WGS 84 / Pseudo-Mercator\"," +
			"       GEOGCS[\"WGS 84\"," +
			"           DATUM[\"WGS_1984\"," +
			"               SPHEROID[\"WGS 84\",6378137,298.257223563," +
			"                   AUTHORITY[\"EPSG\",\"7030\"]]," +
			"               AUTHORITY[\"EPSG\",\"6326\"]]," +
			"           PRIMEM[\"Greenwich\",0," +
			"               AUTHORITY[\"EPSG\",\"8901\"]]," +
			"           UNIT[\"degree\",0.0174532925199433," +
			"               AUTHORITY[\"EPSG\",\"9122\"]]," +
			"           AUTHORITY[\"EPSG\",\"4326\"]]," +
			"       PROJECTION[\"Mercator_1SP\"]," +
			"       PARAMETER[\"central_meridian\",0]," +
			"       PARAMETER[\"scale_factor\",1]," +
			"       PARAMETER[\"false_easting\",0]," +
			"       PARAMETER[\"false_northing\",0]," +
			"       UNIT[\"metre\",1," +
			"           AUTHORITY[\"EPSG\",\"9001\"]]," +
			"       AXIS[\"X\",EAST]," +
			"       AXIS[\"Y\",NORTH]," +
			"       EXTENSION[\"PROJ4\",\"+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs\"]," +
			"       AUTHORITY[\"EPSG\",\"3857\"]]",
			"WGS84 web mercator"
			);
					
			
	public List<SamplingDesignItem> generate(
			double boundaryLonMin, double boundaryLonMax,
			double boundaryLatMin, double boundaryLatMax, 
			int numPlots, Distribution plotDistribution, double plotResolution, double plotWidth,
			int samplesPerPlot, Distribution sampleDistribution, double sampleResolution, double sampleWidth) {
		//TODO use sampleWidth ?
		double aoiWidth = calculateAoiWidth(boundaryLonMin, boundaryLonMax, boundaryLatMin, boundaryLatMax);
		
		Coordinate latLonAoiCenter = new Coordinate(boundaryLonMin + (boundaryLonMax - boundaryLonMin) / 2, 
				boundaryLatMin + (boundaryLatMax - boundaryLatMin) / 2, LAT_LON_SRS_ID);
		
		Coordinate reprojectedAoiCenter = reprojectFromLatLonToWebMarcator(latLonAoiCenter);
		
		List<SamplingDesignItem> items = new ArrayList<SamplingDesignItem>(numPlots + numPlots * samplesPerPlot);
		
		List<Coordinate> plotLocations = generateLocationsInCircle(reprojectedAoiCenter, aoiWidth / 2, numPlots, plotResolution, plotDistribution, plotWidth / 2);
		
		for (int plotIdx = 0; plotIdx < plotLocations.size(); plotIdx++) {
			Coordinate plotCenter = plotLocations.get(plotIdx);

			Coordinate latLonPlotCenter = reprojectFromWebMarcatorToLatLon(plotCenter);
			
			SamplingDesignItem plotCenterItem = new SamplingDesignItem();
			plotCenterItem.setSrsId(LAT_LON_SRS_ID);
			plotCenterItem.setLevelCodes(Arrays.asList(String.valueOf(plotIdx + 1)));
			plotCenterItem.setX(latLonPlotCenter.getX());
			plotCenterItem.setY(latLonPlotCenter.getY());
			items.add(plotCenterItem);
			
			List<Coordinate> sampleLocations = generateLocationsInCircle(plotCenter, plotWidth / 2, samplesPerPlot, sampleResolution, sampleDistribution, sampleWidth / 2);
			for (int sampleIdx = 0; sampleIdx < sampleLocations.size(); sampleIdx++) {
				Coordinate sampleLocation = sampleLocations.get(sampleIdx);
				Coordinate latLonSampleLocation = reprojectFromWebMarcatorToLatLon(sampleLocation);
				SamplingDesignItem sampleItem = new SamplingDesignItem();
				sampleItem.setLevelCodes(Arrays.asList(String.valueOf(plotIdx + 1), String.valueOf(sampleIdx + 1)));
				sampleItem.setSrsId(LAT_LON_SRS_ID);
				sampleItem.setX(latLonSampleLocation.getX());
				sampleItem.setY(latLonSampleLocation.getY());
				items.add(sampleItem);
			}
		}
		return items;
	}

	public double calculateAoiWidth(double boundaryLonMin, double boundaryLonMax, double boundaryLatMin,
			double boundaryLatMax) {
		Coordinate topLeftLatLonCoordinate = new Coordinate(boundaryLonMin, boundaryLatMax, LAT_LON_SRS_ID);
		Coordinate bottomRightLatLonCoordinate = new Coordinate(boundaryLonMax, boundaryLatMin, LAT_LON_SRS_ID);
		
		Coordinate topLeftReprojectedCoordinate = reprojectFromLatLonToWebMarcator(topLeftLatLonCoordinate);
		Coordinate bottomRightReprojectedCoordinate = reprojectFromLatLonToWebMarcator(bottomRightLatLonCoordinate);
		double aoiWidth = bottomRightReprojectedCoordinate.getX() - topLeftReprojectedCoordinate.getX();
		return aoiWidth;
	}

	private List<Coordinate> generateLocationsInCircle(Coordinate center, double circleRadius,
			int numberOfLocations, double resolution, Distribution distribution, double locationRadius) {
		List<Coordinate> result = new ArrayList<Coordinate>(numberOfLocations);
		double radiusSquared = circleRadius * circleRadius;
		switch(distribution) {
		case RANDOM:
			int count = 0;
			while (count < numberOfLocations) {
				double offsetAngle = Math.random() * Math.PI * 2;
				double offsetMagnitude = Math.random() * circleRadius;
				double xOffset = offsetMagnitude * Math.cos(offsetAngle);
				double yOffset = offsetMagnitude * Math.sin(offsetAngle);
				double x = center.getX() + xOffset;
				double y = center.getY() + yOffset;
				
				if (squareDistance(x, y, center.getX(), center.getY()) < radiusSquared) {
					result.add(new Coordinate(x, y,	center.getSrsId()));
					count ++;
				}
			}
			break;
		case GRIDDED:
			double left = center.getX() - circleRadius;
			double top = center.getY() - circleRadius;
			double numberOfSteps = Math.floor(2 * circleRadius / resolution);
			for (int xStep = 0; xStep < numberOfSteps; xStep++) {
				double x = left + xStep * resolution + locationRadius;
				for (int yStep = 0; yStep < numberOfSteps; yStep++) {
					double y = top + yStep * resolution + locationRadius;
					if (squareDistance(x, y, center.getX(), center.getY()) < radiusSquared) {
						result.add(new Coordinate(x, y,	center.getSrsId()));
					}
				}
			}
		}
		return result;
	}
	
	private double squareDistance(double x1, double y1, double x2, double y2) {
		return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
	}
	
	private Coordinate reprojectFromLatLonToWebMarcator(Coordinate coordinate) {
		double[] reprojectedPoint = CoordinateUtils.transform(SpatialReferenceSystem.LAT_LON_SRS, 
				new double[]{coordinate.getX(), coordinate.getY()}, WEB_MARCATOR_SRS);
		return new Coordinate(reprojectedPoint[0], reprojectedPoint[1], WEB_MARCATOR_SRS.getId());
	}
	
	private Coordinate reprojectFromWebMarcatorToLatLon(Coordinate coordinate) {
		double[] reprojectedPoint = CoordinateUtils.transform(WEB_MARCATOR_SRS, 
				new double[]{coordinate.getX(), coordinate.getY()}, LAT_LON_SRS);
		return new Coordinate(reprojectedPoint[0], reprojectedPoint[1], LAT_LON_SRS.getId());
	}
	
}