package org.tactical.sports.client.view.playground.grid;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public class GridPositionHelper {

	private GridElementModel m_gridElementModel;

	public void setGridElementModel(GridElementModel tileModel) {
		m_gridElementModel = tileModel;
	}

	public TileIndex getGridIndexFromPosition(int coordX, int coordY) {
		int alpha = (int) (coordX / (m_gridElementModel.getWidth() * 0.5));
		int beta = (int) (coordY / (m_gridElementModel.getHeight() * 0.5));
		TileIndex index = new TileIndex(0, 0);
		
		if ((alpha % 3) == 2) {
			int col = alpha - (alpha + 1) / 3;
			index.x = col;
			if ((beta % 2) == 1) {
				index.y = (beta - 1) / 2;
				GridPosition position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
			} else {
				index.y =  beta / 2;
				GridPosition position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
				index.y = index.y - 1;
				position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
			}
		} else {
			int col = alpha - alpha / 3;
			index.x = col;
			if ((beta % 2) == 1) {
				index.y = (beta - 1) / 2;
				GridPosition position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
				index.x = index.x - 1;
				position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
			} else {
				int row = beta / 2;
				index.y = row;
				GridPosition position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
				int previousCol = col - 1;
				index.x = previousCol;
				position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
				int previousRow = row - 1;
				index.y = previousRow;
				index.x = col;
				position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
				index.x = previousCol;
				position = getGridPosition(index);
				if (m_gridElementModel.isInWorkingArea(coordX - position.getLeft(), coordY - position.getTop())) {
					return index;
				}
			}
		}
		return null;
	}

	public GridPosition getGridPosition(TileIndex index) {
		int x = (int) (m_gridElementModel.getWidth() * index.x * 0.75f);
		int y = (int) (m_gridElementModel.getHeight() * ((index.x % 2) == 1 ? index.y : index.y + 0.5f));
		
		return new GridPosition(y, x);
	}

	public int getGridElementWidth() {
		return m_gridElementModel.getWidth();
	}

	public int getGridElementHeight() {
		return m_gridElementModel.getHeight();
	}

	public int getWidth(int colCount) {
		return (int) (getGridElementWidth() * (colCount * 0.75f + 0.25f));
	}

	public int getHeight(int rowCount) {
		return getGridElementHeight() * rowCount;
	}
}
