import React, { Component } from "react";

class FriendList extends Component {
    render() {
        if (!this.props.friends) {
            return <div>No Friends yet...</div>
        }
        return (
            <ul id="friend-list">
                {this.props.friends.map(friend => (
                    <li key={friend}>
                        {friend}
                    </li>
                ))}
            </ul>
        );
    }
}

export default FriendList;