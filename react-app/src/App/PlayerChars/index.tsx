import React from 'react';
import { Link } from 'react-router-dom';
import { Card, Grid, List, ListItem, ListItemText } from '@material-ui/core';
import { Character as CharacterInterface } from '../../models/Character.model';
import './index.css';

type Props = { limit?: number };

type State = {
  characters: CharacterInterface[];
  index: number;
};

class PlayerChars extends React.Component<Props, State> {
  componentDidMount() {
    this.setState({
      characters: [
        { id: '3ali-9a83-aos8-3a8n', name: 'Finrod', class: 'Wizard', level: 5 },
        { id: '2ati-9a82-tos8-2atn', name: 'Melda', class: 'Medic', level: 7 },
      ],
      index: 100,
    });
  }

  render() {
    return (
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Card>
            <List>
              <ListItem>
                <ListItemText primary="Your Characters" />
              </ListItem>
              <Link className="btn-link" to="/character/new">
                <ListItem button>
                  <ListItemText primary="Create New Character" />
                </ListItem>
              </Link>
              {this.state?.characters?.map((character, index) => (
                <ListItem
                  /*                   selected={this.is_selected_item(index)}
                                    onClick={() => this.onSelect(index)}
                                    onMouseEnter={() => this.setState({ mouseOn: index })}
                                    onMouseLeave={() => this.setState({ mouseOn: 100 })} */
                  button
                  key={character.name}
                >
                  <ListItemText primary={character.name} />
                  <ListItemText secondary={character.class} />
                  Level: {character.level}
                </ListItem>
              ))}
            </List>
          </Card>
        </Grid>
      </Grid>
    );
  }
}

export default PlayerChars;
