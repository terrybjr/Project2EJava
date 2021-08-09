import React from 'react';
import { Link } from 'react-router-dom';
import { Button, Card, Grid, List, ListItem, ListItemText } from '@material-ui/core';
import { Character as CharacterInterface } from '../../models/Character.model';
import './index.css';

type State = {
  characters: CharacterInterface[];
  index: number;
};

class PlayerChars extends React.Component {
  state: State = {
    characters: [{ name: 'Finrod', class: 'Wizard', level: 5 }, { name: 'Melda', class: 'Medic', level: 7 }],
    index: 100,
  };

  render() {
    return (
      // <div>Player_Chars</div>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Card>
            <List>
              <ListItem>
                <ListItemText primary="Your Characters" />
              </ListItem>
              {this.state.characters.map((character, index) => (
                <ListItem
                  /*                   selected={this.is_selected_item(index)}
                                    onClick={() => this.onSelect(index)}
                                    onMouseEnter={() => this.setState({ mouseOn: index })}
                                    onMouseLeave={() => this.setState({ mouseOn: 100 })} */
                  button
                  key={character.name}
                >
                  <ListItemText
                    primary={character.name}
                  />
                  <ListItemText
                    secondary={character.class}
                  />
                  Level: {character.level}
                </ListItem>
              ))}
            </List>
          </Card>
        </Grid>
        <Grid item xs={12}>
          <Card> Click on Character to view or update
            <Link className="btn-link" to='/character/new'><Button className="button" variant="contained">Create New Character</Button></Link>
          </Card>
        </Grid>
      </Grid>

    );
  }
}

export default PlayerChars;